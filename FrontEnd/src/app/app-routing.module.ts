import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthInterceptor } from './core/interceptors/auth.interceptor';

const routes: Routes = [
   { path: '', loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule) },
   { path: 'error', loadChildren: () => import('./modules/error/error.module').then(m => m.ErrorModule) },
   { path: 'profile', loadChildren: () => import('./modules/profile/profile.module').then(m => m.ProfileModule) },
   { path: 'results', loadChildren: () => import('./modules/results/results.module').then(m => m.ResultsModule) },
   { path: 'results/:params', loadChildren: () => import('./modules/results/results.module').then(m => m.ResultsModule) },
   { path: 'favourites', loadChildren: () => import('./modules/results/results.module').then(m => m.ResultsModule) },
   { path: 'listing/:id', loadChildren: () => import('./modules/listing/listing.module').then(m => m.ListingModule) },
   { path: 'travels', loadChildren: () => import('./modules/travels/travels.module').then(m => m.TravelsModule) },
   { path: 'postings', loadChildren: () => import('./modules/postings/postings.module').then(m => m.PostingsModule) },
   { path: 'rentOut', loadChildren: () => import('./modules/rent-out/rent-out.module').then(m => m.RentOutModule) },
   { path: 'hostings', loadChildren: () => import('./modules/hostings/hostings.module').then(m => m.HostingsModule) }
];

@NgModule({
   providers: [
      {
         provide: HTTP_INTERCEPTORS,
         useClass: AuthInterceptor,
         multi: true
      }
   ],
   imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'enabled' , anchorScrolling: 'enabled'})],
   exports: [RouterModule]
})
export class AppRoutingModule { }
