import { Listing } from './../interfaces/listing.interface';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { catchError, tap, throwError } from 'rxjs';

@Injectable({
   providedIn: 'root'
})
export class ListingService {

   constructor(private http: HttpClient) { }

   getAll() {
      return this.http.get<Listing[]>(`${environment.api}/listing`).pipe(
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
         default:
            return throwError(() => new Error("Errore della chiamata"));
      };
   }
}
