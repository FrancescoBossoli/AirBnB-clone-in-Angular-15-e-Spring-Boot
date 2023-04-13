import { ListingService } from 'src/app/core/services/listing.service';
import { Router } from '@angular/router';
import { AuthService } from './../../services/auth.service';
import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { User } from '../../interfaces/user.interface';
import { environment } from 'src/environments/environment.development';
import { NgbCalendar, NgbDate, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Place } from '../../interfaces/place.interface';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

   emptyUser:User = Object.assign({});
   user:User = Object.assign({});
   darkMode:boolean = false;
   route = this.router.url;
   places:Place[] = [];
   screen:number = window.innerWidth;

   @ViewChild('mainPanel') mainPanel!:ElementRef;
   @ViewChild('radio1') radio1!:ElementRef;
   @ViewChild('radio2') radio2!:ElementRef;
   @ViewChild('radio3') radio3!:ElementRef;
   @ViewChild('searchbar') searchbar!:ElementRef;
   @ViewChild('suggestedSearch') suggestedSearch!:ElementRef;
   @ViewChild('panelCalendar') panelCalendar!:ElementRef;
   @ViewChild('dates') dates!:ElementRef;
   @ViewChild('guests') guests!:ElementRef;
   @ViewChild('guestCount') guestCount!:ElementRef;
   @ViewChild('close') close!:ElementRef;

   searchInput:string = '';
   adults:string = '0';
   children:string = '0';

   hoveredDate: NgbDate|null = null;
	fromDate: NgbDate|null = null;
	toDate: NgbDate|null = null;
   minDate: NgbDate = this.calendar.getNext(this.calendar.getToday(), 'd', 1);

   get avatar() {
      return this.user?.pictureUrl.startsWith('BackEnd') ? environment.backEnd + this.user?.pictureUrl : this.user?.pictureUrl + environment.thumbnail;
   }

   constructor(private authServ: AuthService, private router: Router, private modalServ: NgbModal, private listSrv: ListingService, private renderer: Renderer2, private calendar: NgbCalendar) { }

   ngOnInit(): void {
      this.authServ.user$.subscribe({ next: (user) => { this.user = {...this.emptyUser, ...user} }});
   }

   logout():void {
      this.authServ.logout();
      this.modalServ.dismissAll;
   }

   switchTheme() {
      document.body.classList.toggle("darkMode");
      document.body.classList.toggle("lightMode");
      this.darkMode = !this.darkMode;
   }

   openProfile() {
      this.router.navigate(['profile'])
   }

   openTravels() {
      this.router.navigate(['travels'])
   }

   openRentOut() {
      this.router.navigate(['rentOut'])
   }

   openHostings() {
      this.router.navigate(['hostings'])
   }

   openPanel() {
      this.mainPanel.nativeElement.classList.toggle('d-none');
      this.searchbar.nativeElement.classList.toggle('d-none');
      this.searchbar.nativeElement.classList.toggle('d-flex');
      this.searchbar.nativeElement.parentElement.classList.toggle('border');
      this.close.nativeElement.classList.toggle('d-none');
   }

   cancelSearch() {
      this.openPanel();
      this.panelCalendar.nativeElement.classList.add('d-none');
      this.guestCount.nativeElement.classList.add('d-none');
      this.suggestedSearch.nativeElement.classList.add('d-none');
      this.fromDate = null;
      this.toDate = null;
      // this.destination = '';
      this.searchInput = '';
      this.adults = '0';
      this.children = '0';
      this.dates.nativeElement.innerText = 'Scegli date';
      this.guests.nativeElement.innerText = 'Aggiungi ospiti';

   }

   focus(num:number) {
      switch(num) {
         case 1:
            this.radio1.nativeElement.checked = !this.radio1.nativeElement.checked;
            this.panelCalendar.nativeElement.classList.add('d-none');
            this.guestCount.nativeElement.classList.add('d-none');
            break;
         case 2:
            this.radio2.nativeElement.checked = !this.radio2.nativeElement.checked;
            this.guestCount.nativeElement.classList.add('d-none');
            this.suggestedSearch.nativeElement.classList.add('d-none');
            this.panelCalendar.nativeElement.classList.remove('d-none');
            break;
         case 3:
            this.radio3.nativeElement.checked = !this.radio3.nativeElement.checked;
            this.panelCalendar.nativeElement.classList.add('d-none');
            this.suggestedSearch.nativeElement.classList.add('d-none');
            this.guestCount.nativeElement.classList.remove('d-none');
            break;
      }
   }

   search() {
      if (this.searchInput.length > 2) {
         this.listSrv.partialLocationSearch(this.searchInput).subscribe((res) => {
            this.places = res;
            if (this.places.length > 0) {
               this.suggestedSearch.nativeElement.classList.remove('d-none');
               this.suggestedSearch.nativeElement.removeChild(this.suggestedSearch.nativeElement.children[1]);
               const ul: HTMLUListElement = this.renderer.createElement('ul');
               this.suggestedSearch.nativeElement.appendChild(ul);
               this.places.forEach(e => {
                  let li:HTMLLIElement = this.renderer.createElement('li');
                  li.innerHTML = '<i class="fa-solid fa-location-dot"></i> ' + e.location;
                  li.style.cursor = 'pointer';
                  li.addEventListener('click', () => { this.setDestination(e.location) });
                  ul.append(li);
               })
            }
         })
      }
   }

   setDestination(loc:string) {
      // this.destination = loc;
      this.searchInput = loc;
      this.suggestedSearch.nativeElement.classList.add('d-none');
      this.focus(2);
   }

   onDateSelection(date: NgbDate) {
		if (!this.fromDate && !this.toDate) {
			this.fromDate = date;
         this.dates.nativeElement.innerText = date.day + '/' + date.month + '/' + date.year;
		} else if (this.fromDate && !this.toDate && date.after(this.fromDate)) {
			this.toDate = date;
         this.dates.nativeElement.innerText = this.fromDate.day + '/' + this.fromDate.month + ' - ' + date.day + '/' + date.month;
         this.focus(3);
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

   adultsUp() {
      let count = Number(this.adults)+1;
      this.adults = count.toString();
      this.guests.nativeElement.innerText = this.countPeople().toString();
   }

   adultsDown() {
      let count = Number(this.adults)-1;
      if (count < 1) count = 0;
      this.adults = count.toString();
      this.guests.nativeElement.innerText = this.countPeople().toString();
   }

   childrenUp() {
      let count = Number(this.children)+1;
      this.children = count.toString();
      this.guests.nativeElement.innerText = this.countPeople().toString();
   }

   childrenDown() {
      let count = Number(this.children)-1;
      if (count < 1) count = 0;
      this.children = count.toString();
      this.guests.nativeElement.innerText = this.countPeople().toString();
   }

   countPeople():number {
      return Number(this.adults) + Number(this.children)
   }

   searchForListings() {
      let params:string = '?';
      let arrival:string = '';
      let departure:string = '';
      if (this.fromDate != null) {
         let aMonth:string;
         let aDay:string;
         this.fromDate.month.toString().length == 1 ? aMonth = '0' + this.fromDate.month.toString() : aMonth = '' + this.fromDate.month.toString();
         this.fromDate.day.toString().length == 1 ? aDay = '0' + this.fromDate.day.toString() : aDay = '' + this.fromDate.day.toString();
         arrival = this.fromDate.year + '-' + aMonth + '-' + aDay;
      }
      if (this.toDate != null) {
         let dMonth:string;
         let dDay:string;
         this.toDate.month.toString().length == 1 ? dMonth = '0' + this.toDate.month.toString() : dMonth = '' + this.toDate.month.toString();
         this.toDate.day.toString().length == 1 ? dDay = '0' + this.toDate.day.toString() : dDay = '' + this.toDate.day.toString();
         departure = this.toDate.year + '-' + dMonth + '-' + dDay;
      }
      if (this.searchInput != '') params += `location=${this.searchInput.replace(', ', '-')}&`;
      if (arrival != '') params += `arrival=${arrival}&`;
      if (departure != '') params += `departure=${departure}&`;
      if (this.countPeople() != 0) params += `guests=${this.countPeople()}&`;
      this.cancelSearch();
      this.router.navigateByUrl(`results/${params.substring(0,params.length -1)}`);

   }
}
