import { AuthService } from 'src/app/core/services/auth.service';
import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { User } from '../interfaces/user.interface';
import { JwtResponse } from '../interfaces/jwt-response.interface';


export const profileResolver: ResolveFn<User> = () => {
   const authServ = inject(AuthService);
   let emptyUser = Object.assign({});
   let user:User = Object.assign({});
   authServ.user$.subscribe({ next: (data) => { user = {...emptyUser, ...data} }})
   if (user!.surname != null) return user;
   let emptyJwt = Object.assign({});
   let jwt:JwtResponse = {...emptyJwt, ...user};
   console.log(jwt)
   return authServ.loggedUserData(jwt);
};


