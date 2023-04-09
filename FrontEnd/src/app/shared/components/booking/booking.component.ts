import { CommonModule } from '@angular/common';
import { AfterViewInit, ChangeDetectionStrategy, Component, ElementRef, Input, ViewChild } from '@angular/core';
import { RouterModule } from '@angular/router';
import * as mapboxgl from 'mapbox-gl';
import { Booking } from 'src/app/core/interfaces/booking.interface';
import { environment } from 'src/environments/environment.development';

@Component({
    selector: 'app-booking',
    templateUrl: './booking.component.html',
    styleUrls: ['./booking.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    standalone: true,
    imports: [CommonModule, RouterModule]
})
export class BookingComponent implements AfterViewInit{

   @Input('booking') booking: Booking = Object.assign({});

   @ViewChild('smallMap') smallMap!:ElementRef;

   map!: mapboxgl.Map;
   style = 'mapbox://styles/mapbox/streets-v11';

   ngAfterViewInit(): void {
      console.log(this.smallMap)
      this.map = new mapboxgl.Map({
         accessToken: environment.mapToken,
         container: this.smallMap.nativeElement,
         style: this.style,
         zoom: 11
      });
      const marker = new mapboxgl.Marker({
         draggable:false,
         color:'#7A88EC'
      })
      .setLngLat([this.booking.location.longitude, this.booking.location.latitude])
      .addTo(this.map);
      this.map.setCenter([this.booking.location.longitude, this.booking.location.latitude]);
   }


}
