import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { profileResolver } from 'src/app/core/resolvers/profile.resolver';
import { ProfileComponent } from './profile.component';

const routes: Routes = [{ path: '', component: ProfileComponent, resolve: { user: profileResolver } }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }
