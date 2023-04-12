import { environment } from 'src/environments/environment.development';
import { BookingRequest } from './../../core/interfaces/booking-request.interface';
import { BookingService } from './../../core/services/booking.service';
import { ModalService } from './../../core/services/modal.service';
import { AuthService } from 'src/app/core/services/auth.service';
import { Listing } from './../../core/interfaces/listing.interface';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit, TemplateRef, ViewEncapsulation } from '@angular/core';
import { of, switchMap } from 'rxjs';
import { User } from 'src/app/core/interfaces/user.interface';
import { ListingService } from 'src/app/core/services/listing.service';
import { NgbCalendar, NgbDate, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-listing',
  templateUrl: './listing.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./listing.component.scss']
})
export class ListingComponent implements OnInit {

   listing:Listing = Object.assign({});
   emptyUser:User = Object.assign({});
   user:User = Object.assign({});
   isLogged:boolean = false;
   isFavourite:boolean = false;
   screen:number = window.innerWidth;
   services:number[] = [];
   reviewsPage:number = 1;

   hoveredDate: NgbDate|null = null;
	fromDate: NgbDate|null = null;
	toDate: NgbDate|null = null;
   minDate: NgbDate = this.calendar.getNext(this.calendar.getToday(), 'd', 1);
   bookedDays: NgbDate[] = [];
   guests:number|null = null;
   guestsText:string = 'Numero di ospiti'

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

   get days():number {
      let days = 0;
      let start = new Date(this.fromDate?.year + '-' + this.fromDate?.month + '-' + this.fromDate?.day);
      let end = new Date(this.toDate?.year + '-' + this.toDate?.month + '-' + this.toDate?.day);
      for (let i = start; i < end; i.setDate(i.getDate() + 1)) days++;
      return days;
   }

   constructor(private route: ActivatedRoute, private authSrv: AuthService, private listServ: ListingService, private modalServ: NgbModal, private calendar: NgbCalendar, private modSrv: ModalService, private bookSrv: BookingService, private router: Router) { }

   ngOnInit(): void {
      this.route.data.pipe(switchMap(data => of(data['listing']))).subscribe((res) => {this.listing = res; console.log(this.listing)});
      this.authSrv.isLoggedIn$.subscribe((res) => this.isLogged = res);
      this.authSrv.user$.subscribe((res) => this.user = {...this.emptyUser, ...res} );
      if (this.isLogged == true) {
         for (let i = 0; i < this.user.favourites.length; i++) {
            if (this.user.favourites[i].id == this.listing.id) this.isFavourite = true;
         }
      }
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
      console.log(this.bookedDays)
   }

   setFavourite() {
      if (this.isFavourite) this.listServ.removeFavourite(this.listing.id).subscribe(() => this.user.favourites.splice(this.user.favourites.indexOf(this.listing),1));
      else this.listServ.setFavourite(this.listing.id).subscribe(() => this.user.favourites.push(this.listing));
      localStorage.removeItem("user");
      localStorage.setItem("user", JSON.stringify({"token":this.user.token, "id":this.user.id, "username":this.user.username, "email":this.user.email, "roles":this.user.roles, "name":this.user.name, "pictureUrl":this.user.pictureUrl, "favourites":this.user.favourites}));
      this.isFavourite = !this.isFavourite;
   }

   enlarge(template: TemplateRef<any>, letter: string) {
      const animation = letter == "f" ? "fadeIn" : "rotate2";
      let size = 'lg';
      if (window.innerWidth >= 1200) size = "xl";
      this.modalServ.open(template, { centered: true, windowClass: animation, size: size });
   }

   onDateSelection(date: NgbDate) {
		if (!this.fromDate && !this.toDate) {
			this.fromDate = date;
		} else if (this.fromDate && !this.toDate && date.after(this.fromDate)) {
         let check = false;
         for (let x = new Date(this.fromDate.year + "-" + this.fromDate.month + "-" + this.fromDate.day); x < new Date(date.year + "-" + date.month + "-" + date.day); x.setDate(x.getDate() + 1)) {
            for (let y = 0; y < this.bookedDays.length; y++) {
               if (x.getTime() == (new Date(this.bookedDays[y].year + "-" + this.bookedDays[y].month + "-" + this.bookedDays[y].day)).getTime()) check = true;
            }
         }
         check == false ? this.toDate = date : this.fromDate = date;
		} else {
			this.toDate = null;
			this.fromDate = date;
		}
	}

	isHovered(date: NgbDate) {
		return (
			this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate)
		);
	}

	isInside(date: NgbDate) {
		return this.toDate && date.after(this.fromDate) && date.before(this.toDate);
	}

	isRange(date: NgbDate) {
		return (
			date.equals(this.fromDate) ||
			(this.toDate && date.equals(this.toDate)) ||
			this.isInside(date) ||
			this.isHovered(date)
		);
	}

   isFirst(date: NgbDate) {
		return date.equals(this.fromDate);
	}

   isLast(date: NgbDate) {
		return date.equals(this.toDate);
	}

   isDisabled = (date:NgbDate, current?: {month: number,year:number}) => this.bookedDays.find(x => x.equals(date))?true:false;

   setGuests(num:number) {
      this.guests = num;
      num == 1 ? this.guestsText = '1 ospite' : this.guestsText = `${num} ospiti`;
   }

   book(error: TemplateRef<any>, payment:TemplateRef<any>) {
      if (!this.isLogged) this.modSrv.loginRequestNeeded(true);
      else {
         if (this.fromDate != null && this.toDate != null && this.guests != null) {
            this.modalServ.open(payment, { centered: true, size: 'sm' });
         }
         else {
            this.modalServ.open(error, { centered: true, size: 'sm' });
         }
      }
   }

   proceedBooking() {
      let aMonth:string;
      let aDay:string;
      let dMonth:string;
      let dDay:string;
      this.fromDate?.month.toString().length == 1 ? aMonth = '0' + this.fromDate?.month.toString() : aMonth = '' + this.fromDate?.month.toString();
      this.fromDate?.day.toString().length == 1 ? aDay = '0' + this.fromDate?.day.toString() : aDay = '' + this.fromDate?.day.toString();
      this.toDate?.month.toString().length == 1 ? dMonth = '0' + this.toDate?.month.toString() : dMonth = '' + this.toDate?.month.toString();
      this.toDate?.day.toString().length == 1 ? dDay = '0' + this.toDate?.day.toString() : dDay = '' + this.toDate?.day.toString();
      let request:BookingRequest = { arrival: this.fromDate?.year + '-' + aMonth + '-' + aDay,
                                     departure: this.toDate?.year + '-' + dMonth + '-' + dDay,
                                     cost: this.listing.price * this.days,
                                     locationId: this.listing.id,
                                     userId: this.user.id
                                    };
      console.log(request)
      this.bookSrv.registerBooking(request).subscribe(() => this.router.navigate(['travels']));
   }


}
