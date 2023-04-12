import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RentOutComponent } from './rent-out.component';
import { authGuard } from 'src/app/core/guards/auth.guard';
import { userResolver } from 'src/app/core/resolvers/user.resolver';

const routes: Routes = [{ path: '', component: RentOutComponent, canMatch:[authGuard], resolve: { user: userResolver } }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RentOutRoutingModule { }
