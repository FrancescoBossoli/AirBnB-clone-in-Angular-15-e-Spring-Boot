import { User } from './../interfaces/user.interface';
import { JwtResponse } from './../interfaces/jwt-response.interface';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject, catchError, map, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SignupData } from '../interfaces/signup-data.interface';

@Injectable({
   providedIn: 'root'
})
export class AuthService {

   private authSubject = new BehaviorSubject<User | null>(null);
   user$ = this.authSubject.asObservable();
   isLoggedIn$ = this.user$.pipe(map(user => !!user));
   autoLogoutTimer: any;

   constructor(private http: HttpClient, private router: Router, private jwtHelper: JwtHelperService) {
      this.restoreSession();
   }

   signup(data: SignupData) {
      return this.http.post<JwtResponse>(`${environment.apiUrl}/register`, data).pipe(catchError(this.errors));
   }

   login(data: { username: string, password: string }) {
      return this.http.post<JwtResponse>(`${environment.apiUrl}/login`, data).pipe(
         tap((res) => console.log(res)),
         tap((res) => {
            this.authSubject.next({ id: res.id, username: res.username, email: res.email, roles: res.roles });
            localStorage.setItem("user", JSON.stringify(res));
            const expirationDate = this.jwtHelper.getTokenExpirationDate(res.token) as Date;
            this.autoLogout(expirationDate);
         }),
         catchError(this.errors)
      );
   }

   autoLogout(expirationDate: Date) {
      this.autoLogoutTimer.setTimeout(() => this.logout(), expirationDate.getTime() - new Date().getTime());
   }

   logout() {
      this.authSubject.next(null);
      this.router.navigate(['/login']);
      localStorage.removeItem('user');
      if (this.autoLogoutTimer) clearTimeout(this.autoLogoutTimer);
   }

   restoreSession() {
      const userData = localStorage.getItem('user');
      if (!userData) return;
      const jwt: JwtResponse = JSON.parse(userData);
      if (this.jwtHelper.isTokenExpired(jwt.token)) return;
      this.authSubject.next({ id: jwt.id, username: jwt.username, email: jwt.email, roles: jwt.roles });
      const expirationDate = this.jwtHelper.getTokenExpirationDate(jwt.token) as Date;
      this.autoLogout(expirationDate);
   }

   private errors(err: any) {
      switch (err.error) {
         case "Email and password are required":
            return throwError(() => new Error("L'e-mail e la Password sono necessarie"));
         case "Email already exists":
            return throwError(() => new Error("L'e-mail risulta già associata ad un Account esistente"));
         case "Email is invalid":
            return throwError(() => new Error("L'e-mail inserita non sembra essere formalmente corretta"));
         case "Cannot find user":
            return throwError(() => new Error("Non è stato possibile trovare un Account associato alle credenziali inserite"));
         default:
            return throwError(() => new Error("Errore della chiamata"));
      };
   }
}
