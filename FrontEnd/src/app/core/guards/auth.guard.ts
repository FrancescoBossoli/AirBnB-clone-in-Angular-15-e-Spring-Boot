import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';
import { CanActivateFn, Router, CanMatchFn } from '@angular/router';

export const authGuard: CanActivateFn | CanMatchFn = () => {

   const authSrv = inject(AuthService);
   const router = inject(Router);
   let isLogged:boolean = false;
   authSrv.isLoggedIn$.subscribe((res) => isLogged = res);
   if (isLogged) return true;
   return router.parseUrl('/');

}


