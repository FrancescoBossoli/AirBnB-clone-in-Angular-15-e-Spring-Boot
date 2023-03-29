import { AuthService } from './../services/auth.service';
import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, switchMap } from 'rxjs';
import { JwtResponse } from '../interfaces/jwt-response.interface';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

   constructor(private authServ: AuthService) { }

   intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
      const userData = localStorage.getItem('user');
      if (!userData) return next.handle(request);
      const jwt: JwtResponse = JSON.parse(userData!);
      const jwtHelper = new JwtHelperService();
      if (jwtHelper.isTokenExpired(jwt.token)) return next.handle(request);
      return next.handle(request.clone({ headers: request.headers.set("Authorization", "Bearer " + jwt.token) }));
   }
}
