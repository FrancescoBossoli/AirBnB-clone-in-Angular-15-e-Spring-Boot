import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
   { path: '', loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule) },
   { path: 'error', loadChildren: () => import('./modules/error/error.module').then(m => m.ErrorModule) },
   { path: 'profile', loadChildren: () => import('./modules/profile/profile.module').then(m => m.ProfileModule) }
];

@NgModule({
   imports: [RouterModule.forRoot(routes)],
   exports: [RouterModule]
})
export class AppRoutingModule { }
