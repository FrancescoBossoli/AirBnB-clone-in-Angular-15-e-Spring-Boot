import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RentOutRoutingModule } from './rent-out-routing.module';
import { RentOutComponent } from './rent-out.component';


@NgModule({
  declarations: [
    RentOutComponent
  ],
  imports: [
    CommonModule,
    RentOutRoutingModule
  ]
})
export class RentOutModule { }
