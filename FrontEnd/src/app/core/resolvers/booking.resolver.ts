import { Injectable, inject } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
  ResolveFn
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Booking } from '../interfaces/booking.interface';
import { AuthService } from '../services/auth.service';
import { User } from '../interfaces/user.interface';
import { BookingService } from '../services/booking.service';


export const BookingResolver: ResolveFn<Booking[]> = () => {
   const authServ = inject(AuthService);
   const bookServ = inject(BookingService);
   let emptyUser = Object.assign({});
   let user:User = Object.assign({});
   authServ.user$.subscribe((data) => { user = {...emptyUser, ...data} });
   return bookServ.getBookings(user.id);
}
