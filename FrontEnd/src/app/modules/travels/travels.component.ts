import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { of, switchMap } from 'rxjs';
import { Booking } from 'src/app/core/interfaces/booking.interface';
import { User } from 'src/app/core/interfaces/user.interface';

@Component({
   selector: 'app-travels',
   templateUrl: './travels.component.html',
   styleUrls: ['./travels.component.scss']
})
export class TravelsComponent implements OnInit{

   user: User = Object.assign({});
   pastBookings: Booking[] = [];
   currentBookings: Booking[] = [];
   bookingsPage:number = 1;

   constructor(private route: ActivatedRoute) { }

   ngOnInit(): void {
      this.route.data.pipe(switchMap(data => of(data['bookings']))).subscribe((res:Booking[]) => {
         let bookings = res;
         let today = new Date();
         if (bookings.length > 0) {
            for (let i = 0; i < bookings.length; i++) {
               let arrival = new Date(bookings[i].arrival.toString());
               let departure = new Date(bookings[i].departure.toString());
               if (arrival.getTime() < today.getTime() && departure.getTime() < today.getTime()) {
                  this.pastBookings.push(bookings[i])
               }
               else this.currentBookings.push(bookings[i]);
            }
         }
      });;

      this.sortBookings(this.currentBookings);
      this.sortBookings(this.pastBookings);
      console.log(this.currentBookings)
   }

   sortBookings(collection:Booking[]) {
      return collection.sort((a,b) => {
         const aD = new Date(a.arrival);
         const bD = new Date(b.arrival);
         return aD.getTime() - bD.getTime();
      });
   }
}
