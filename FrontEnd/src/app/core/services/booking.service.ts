import { BookingRequest } from './../interfaces/booking-request.interface';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Booking } from '../interfaces/booking.interface';

@Injectable({
   providedIn: 'root'
})
export class BookingService {

   constructor(private http: HttpClient) { }

   registerBooking(request:BookingRequest) {
      return this.http.post(`${environment.api}/booking`, request).pipe(
         catchError(this.errors)
      );
   }

   getBookings(id:number) {
      return this.http.get<Booking[]>(`${environment.api}/booking/${id}`).pipe(
         catchError(this.errors)
      );
   }

   private errors(err: any) {
      console.log(err)
      switch (err.error.message) {
         case "Error: Listing not Found":
            return throwError(() => new Error("L'alloggio da prenotare non è stato trovato"));
         case "Error: User not found":
            return throwError(() => new Error("L'utente non è stato trovato"));
         default:
            return throwError(() => new Error("Errore della chiamata"));
      };
   }
}
