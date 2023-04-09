import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TravelsComponent } from './travels.component';
import { authGuard } from 'src/app/core/guards/auth.guard';
import { BookingResolver } from 'src/app/core/resolvers/booking.resolver';

const routes: Routes = [{ path: '', component: TravelsComponent, canMatch:[authGuard], resolve: { bookings: BookingResolver } }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TravelsRoutingModule { }
