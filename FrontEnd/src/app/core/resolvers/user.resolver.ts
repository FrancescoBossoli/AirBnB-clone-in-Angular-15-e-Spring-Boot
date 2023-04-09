import { AuthService } from 'src/app/core/services/auth.service';
import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { User } from '../interfaces/user.interface';
import { JwtResponse } from '../interfaces/jwt-response.interface';


export const userResolver: ResolveFn<User> = () => {
   const authServ = inject(AuthService);
   let emptyUser = Object.assign({});
   let user:User = Object.assign({});
   authServ.user$.subscribe({ next: (data) => { user = {...emptyUser, ...data} }})
   let jwt:JwtResponse = {...emptyUser, ...user};
   return authServ.loggedUserData(jwt);
};


