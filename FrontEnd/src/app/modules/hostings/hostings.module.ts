import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HostingsRoutingModule } from './hostings-routing.module';
import { HostingsComponent } from './hostings.component';
import { NgbCarouselModule, NgbDatepicker, NgbPaginationModule, NgbRatingModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    HostingsComponent
  ],
  imports: [
    CommonModule,
    HostingsRoutingModule,
    NgbCarouselModule,
    NgbRatingModule,
    NgbPaginationModule,
    NgbDatepicker
  ]
})
export class HostingsModule { }
