import { ListingSearch } from './../interfaces/listing-search.interface';
import { Listing } from './../interfaces/listing.interface';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { BehaviorSubject, catchError, tap, throwError } from 'rxjs';
import { Place } from '../interfaces/place.interface';

@Injectable({
   providedIn: 'root'
})
export class ListingService {

   results:BehaviorSubject<Listing[]> = new BehaviorSubject<Listing[]>([]);
   results$ = this.results.asObservable();

   constructor(private http: HttpClient) { }

   getAll() {
      return this.http.get<Listing[]>(`${environment.api}/listing`).pipe(
         catchError(this.errors)
      );
   }

   getListing(id:number) {
      return this.http.get<Listing>(`${environment.api}/listing/${id}`).pipe(
         catchError(this.errors)
      );
   }

   setFavourite(id:number) {
      return this.http.post(`${environment.api}/favourite/${id}`, null).pipe(
         catchError(this.errors)
      );
   }

   removeFavourite(id:number) {
      return this.http.delete(`${environment.api}/favourite/${id}`).pipe(
         catchError(this.errors)
      );
   }

   partialLocationSearch(term: string) {
      return this.http.post<Place[]>(`${environment.api}/listing/location`, term).pipe(
         catchError(this.errors)
      );
   }

   listingSearch(form:ListingSearch) {
      return this.http.post<Listing[]>(`${environment.api}/listing`, form).pipe(
         tap((res) => this.results.next(res)),
         catchError(this.errors)
      );
   }

   private errors(err: any) {
      console.log(err)
      switch (err.error.message) {
         case "Error: Listing List not found":
            return throwError(() => new Error("Non sono stati trovati alloggi per l'area impostata"));
         case "Error: Listing not Found":
            return throwError(() => new Error("L'alloggio non è stato trovato"));
         case "Error: User not found":
            return throwError(() => new Error("L'utente non è stato trovato"));
         case "Error: Preferred Listing not Found":
            return throwError(() => new Error("L'inserzione non è stata trovata tra i preferiti"));
         default:
            return throwError(() => new Error("Errore della chiamata"));
      };
   }
}
