import { PaymentComponent } from './../../shared/components/payment/payment.component';
import { NgModule } from '@angular/core';
import { CommonModule, JsonPipe } from '@angular/common';
import { ListingRoutingModule } from './listing-routing.module';
import { ListingComponent } from './listing.component';
import { NgbCarouselModule, NgbDatepickerModule, NgbPaginationModule, NgbRatingModule, NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    ListingComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ListingRoutingModule,
    NgbCarouselModule,
    NgbRatingModule,
    NgbPaginationModule,
    NgbDatepickerModule,
    NgbDropdownModule,
    JsonPipe,
    PaymentComponent
  ]
})
export class ListingModule { }
