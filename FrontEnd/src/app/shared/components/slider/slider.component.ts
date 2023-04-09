import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Booking } from 'src/app/core/interfaces/booking.interface';
import { BookingComponent } from "../booking/booking.component";
import { NextDirective } from '../../directives/next.directive';
import { PreviousDirective } from '../../directives/previous.directive';

@Component({
    selector: 'app-slider',
    templateUrl: './slider.component.html',
    styleUrls: ['./slider.component.scss'],
    standalone: true,
    imports: [
      CommonModule,
      BookingComponent,
      NextDirective,
      PreviousDirective
   ]
})
export class SliderComponent implements OnInit {

  @Input("bookings") bookings:Booking[] = [];

  constructor() { }

  ngOnInit(): void {
  }



}
