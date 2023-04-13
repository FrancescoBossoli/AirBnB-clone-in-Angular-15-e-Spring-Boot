import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HostingsComponent } from './hostings.component';
import { authGuard } from 'src/app/core/guards/auth.guard';
import { userResolver } from 'src/app/core/resolvers/user.resolver';

const routes: Routes = [{ path: '', component: HostingsComponent, canMatch:[authGuard], resolve: { user: userResolver } }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HostingsRoutingModule { }
