import { User } from './../interfaces/user.interface';
import { JwtResponse } from './../interfaces/jwt-response.interface';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject, catchError, map, tap, throwError, Observable, of } from 'rxjs';
import { SignupData } from '../interfaces/signup-data.interface';
import { environment } from 'src/environments/environment.development';

@Injectable({
   providedIn: 'root'
})
export class AuthService {

   jwtHelper = new JwtHelperService();
   private authSubject = new BehaviorSubject<Partial<User>>({});
   user$ = this.authSubject.asObservable();
   isLoggedIn$ = this.user$.pipe(map(user => !!user));
   timeLeft:any;

   constructor(private http: HttpClient, private router: Router) {
      this.restoreSession();
   }

   signup(data: SignupData) {
      return this.http.post<JwtResponse>(`${environment.api}/signup`, data).pipe(
         tap((res) => this.generateToken(res)),
         catchError(this.errors)
      );
   }

   login(credentials: { username: string, password: string }) {
      return this.http.post<JwtResponse>(`${environment.api}/login`, credentials).pipe(
         tap((res) => this.generateToken(res)),
         catchError(this.errors)
      );
   }

   generateToken(userData:JwtResponse) {
      let {token, type, ...user} = userData;
      this.authSubject.next(user);
      localStorage.setItem("user", JSON.stringify({"token":token, "id":user.id, "username":user.username, "email":user.email, "roles":user.roles, "name":user.name, "pictureUrl":user.pictureUrl}));
      this.autoLogout(userData)
   }

   autoLogout(userData:JwtResponse) {
      const expirationDate = this.jwtHelper.getTokenExpirationDate(userData.token) as Date
      this.timeLeft = setTimeout(() => this.logout(), (expirationDate.getTime() - new Date().getTime()));
   }

   logout() {
      this.authSubject.next(({}));
      localStorage.removeItem("user")
      this.router.navigate(['/'])
      if (this.timeLeft) clearTimeout(this.timeLeft)
   }

   restoreSession() {
      const userData = localStorage.getItem('user');
      if (!userData) return;
      const jwt: JwtResponse = JSON.parse(userData);
      if (this.jwtHelper.isTokenExpired(jwt.token)) return;
      this.authSubject.next({ id: jwt.id, username: jwt.username, email: jwt.email, roles: jwt.roles, name: jwt.name, pictureUrl:jwt.pictureUrl, token:jwt.token });
      this.autoLogout(jwt);
   }

   loggedUserData(jwt:JwtResponse):Observable<User> {
      return this.http.post<JwtResponse>(`${environment.api}/restore`, {id: jwt.id, username: jwt.username, token:jwt.token}).pipe(
         map((res) => {
            let {type, ...user} = res;
            this.authSubject.next(user);
            return user;
         }),
         catchError(this.errors)
      );
   }

   private errors(err: any) {
      console.log(err)
      switch (err.error.message) {
         case "Error: Username is already taken!":
            return throwError(() => new Error("L'username è già stato scelto"));
         case "Error: Email is already in use!":
            return throwError(() => new Error("L'e-mail risulta già associata ad un Account esistente"));
         case "Error: Email is invalid!":
            return throwError(() => new Error("L'e-mail inserita non sembra essere formalmente corretta"));
         case "Error: There is no Account associated to this Username!":
            return throwError(() => new Error("L'username non sembra essere associato a un Account esistente"));
         case "Error: User not Found":
            return throwError(() => new Error("L'utente non risulta presente nel database"));
         case "Error: The sent details don't match with the User's ones!":
            return throwError(() => new Error("I dettagli forniti non corrispondono a quelli presenti nel database"));
         default:
            return throwError(() => new Error("Errore della chiamata"));
      };
   }
}
