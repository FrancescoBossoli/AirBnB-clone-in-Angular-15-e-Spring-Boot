import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { userResolver } from 'src/app/core/resolvers/user.resolver';
import { ProfileComponent } from './profile.component';
import { authGuard } from 'src/app/core/guards/auth.guard';

const routes: Routes = [{ path: '', component: ProfileComponent, canMatch:[authGuard], resolve: { user: userResolver } }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }
