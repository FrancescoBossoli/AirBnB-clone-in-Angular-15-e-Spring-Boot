import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthInterceptor } from './core/interceptors/auth.interceptor';

const routes: Routes = [
   { path: '', loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule) },
   { path: 'error', loadChildren: () => import('./modules/error/error.module').then(m => m.ErrorModule) },
   { path: 'profile', loadChildren: () => import('./modules/profile/profile.module').then(m => m.ProfileModule) },
   { path: 'results', loadChildren: () => import('./modules/results/results.module').then(m => m.ResultsModule) },
   { path: 'favourites', loadChildren: () => import('./modules/results/results.module').then(m => m.ResultsModule) },
];

@NgModule({
   providers: [
      {
         provide: HTTP_INTERCEPTORS,
         useClass: AuthInterceptor,
         multi: true
      }
   ],
   imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'enabled' })],
   exports: [RouterModule]
})
export class AppRoutingModule { }
