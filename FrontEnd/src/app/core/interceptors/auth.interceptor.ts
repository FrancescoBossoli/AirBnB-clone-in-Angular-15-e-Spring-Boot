import { AuthService } from './../services/auth.service';
import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, switchMap } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

   constructor(private authServ: AuthService) { }

   intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
      return this.authServ.user$.pipe(
         switchMap((user) => {
            if (user.token == null || user.token == "") return next.handle(request);
            return next.handle(request.clone({ headers: request.headers.set("Authorization", "Bearer " + user.token) }));
         })
      )
   }
}
