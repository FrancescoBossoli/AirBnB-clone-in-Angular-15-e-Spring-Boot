import { Component, OnInit, TemplateRef, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbCalendar, NgbDate, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of, switchMap } from 'rxjs';
import { Listing } from 'src/app/core/interfaces/listing.interface';
import { User } from 'src/app/core/interfaces/user.interface';
import { environment } from 'src/environments/environment.development';

@Component({
  selector: 'app-hostings',
  templateUrl: './hostings.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./hostings.component.scss']
})
export class HostingsComponent implements OnInit {

   user:User = Object.assign({});
   listing:Listing = Object.assign({});
   listingIndex:number = 0;
   screen:number = window.innerWidth;
   services:number[] = [];
   bookedDays: NgbDate[] = [];
   fromDate: NgbDate|null = null;
	toDate: NgbDate|null = null;
   minDate: NgbDate = this.calendar.getNext(this.calendar.getToday(), 'd', 1);
   reviewsPage:number = 1;

   get backEnd(): string { return environment.backEnd }

   get roomType():string {
      switch(this.listing.roomType) {
         case 'ENTIRE_HOME': return 'Intero alloggio'
         case 'PRIVATE_ROOM': return 'Camera privata'
         case 'HOTEL_ROOM': return 'Camera di hotel'
         case 'SHARED_ROOM': return 'Camera condivisa'
         default: return 'Camera'
      }
   }

   get property():string {
      switch(this.listing.propertyType) {
         case 'RENTAL_UNIT': return 'unità in affitto'
         case 'ROOM_IN_RENTAL_UNIT': return 'camera di unità in affitto'
         case 'CONDO': return 'appartamento'
         case 'LOFT': return 'loft'
         case 'ROOM_IN_LOFT': return 'stanza privata in loft'
         case 'GUEST_SUITE': return 'suite'
         case 'HOME': return 'abitazione intera'
         case 'ROOM_IN_HOTEL': return 'camera di hotel'
         case 'ROOM_IN_HOSTEL': return 'camera di ostello'
         case 'ROOM_IN_BNB': return 'camera di b&b'
         default: return 'Abitazione'
      }
   }

   get capacity():string {
      if(this.listing.capacity == 1) return '1 ospite';
      return `${this.listing.capacity} ospiti`
   }

   get bedrooms():string {
      if (this.listing.bedrooms == 1) return '1 camera da letto';
      return `${this.listing.bedrooms} camere da letto`
   }

   get beds():string {
      if (this.listing.beds == 1) return '1 letto';
      return `${this.listing.beds} letti`
   }

   get bathrooms():string {
      switch(this.listing.bathrooms) {
         case 'BATH_1': return '1 bagno'
         case 'BATHS_2': return '2 bagni'
         case 'BATHS_3': return '3 bagni'
         case 'BATHS_4': return '4 bagni'
         case 'PRIVATE_BATH_1': return '1 bagno privato'
         case 'PRIVATE_BATHS_2': return '2 bagni privati'
         case 'SHARED_BATH_1': return '1 bagno condiviso'
         case 'SHARED_BATHS_2': return '2 bagni condivisi'
         case 'SHARED_BATHS_3': return '3 bagni condivisi'
         case 'SHARED_BATHS_4': return '4 bagni condivisi'
         default: return 'Bagno'
      }
   }

   get score(): number {
      let score = 0;
      this.listing.reviews.forEach((e) => score += e.score);
      return score/this.listing.reviews.length;
   }

   isDisabled = (date:NgbDate, current?: {month: number,year:number}) => this.bookedDays.find(x => x.equals(date))?true:false;



   constructor(private route: ActivatedRoute, private modalServ: NgbModal, private calendar: NgbCalendar) { }

   ngOnInit(): void {
      this.route.data.pipe(switchMap(data => of(data['user']))).subscribe((res) => {
         this.user = res;
         this.listing = this.user.listings[this.listingIndex];
         for (let i = 0; i < this.listing.amenities.length; i++) {
            this.services.push(this.listing.amenities[i].id);
         }
         const today = new Date();
         this.listing.bookings.forEach((e) => {
         if (Date.parse(e.departure.toString()) >= today.getTime()) {
            for (let i = new Date(e.arrival); i < new Date(e.departure); i.setDate(i.getDate() + 1)) {
               this.bookedDays.push(new NgbDate(i.getFullYear(), i.getMonth()+1, i.getDate()));
            }
         }
      });
      });
   }

   enlarge(template: TemplateRef<any>, letter: string) {
      const animation = letter == "f" ? "fadeIn" : "rotate2";
      let size = 'lg';
      if (window.innerWidth >= 1200) size = "xl";
      this.modalServ.open(template, { centered: true, windowClass: animation, size: size });
   }

   nextListing() {
      this.listingIndex == this.user.listings.length - 1 ? this.listingIndex = 0 : this.listingIndex++;
      this.listing = this.user.listings[this.listingIndex];
   }

   previousListing() {
      this.listingIndex == 0 ? this.listingIndex = this.user.listings.length - 1 : this.listingIndex--;
      this.listing = this.user.listings[this.listingIndex];
   }
}
