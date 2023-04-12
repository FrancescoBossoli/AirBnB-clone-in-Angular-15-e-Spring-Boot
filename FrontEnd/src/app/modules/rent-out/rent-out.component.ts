import { User } from './../../core/interfaces/user.interface';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { of, switchMap } from 'rxjs';
import { Listing } from 'src/app/core/interfaces/listing.interface';
import { AuthService } from 'src/app/core/services/auth.service';
import { ListingService } from 'src/app/core/services/listing.service';

@Component({
  selector: 'app-rent-out',
  templateUrl: './rent-out.component.html',
  styleUrls: ['./rent-out.component.scss']
})
export class RentOutComponent implements OnInit {

   user:User = Object.assign({});
   image:File[] = [];
   listing:Listing = Object.assign({});
   @ViewChild('locationName') locationName!:ElementRef;
   @ViewChild('locationDescription') locationDescription!:ElementRef;
   @ViewChild('locationNeighborhood') locationNeighborhood!:ElementRef;
   @ViewChild('locationLongitude') locationLongitude!:ElementRef;
   @ViewChild('locationLatitude') locationLatitude!:ElementRef;
   @ViewChild('locationUbication') locationUbication!:ElementRef;
   @ViewChild('roomSelect') roomSelect!:ElementRef;
   @ViewChild('propertySelect') propertySelect!:ElementRef;
   @ViewChild('locationCapacity') locationCapacity!:ElementRef;
   @ViewChild('locationBedrooms') locationBedrooms!:ElementRef;
   @ViewChild('locationBeds') locationBeds!:ElementRef;
   @ViewChild('locationBathroom') locationBathroom!:ElementRef;
   @ViewChild('price') price!:ElementRef;

   constructor(private route: ActivatedRoute, private listServ:ListingService, private authServ:AuthService) { }

   ngOnInit(): void {
      this.route.data.pipe(switchMap(data => of(data['user']))).subscribe((res) => {this.user = res; console.log(this.user)});
   }

   getImage(e:Event) {
      const el = e.currentTarget as HTMLInputElement;
      let fileList:FileList|null = el.files;
      if (fileList) this.image.push(fileList[0]);
      el.labels![0].classList.toggle('bg-primary');
      el.labels![0].classList.toggle('bg-info');
   }

   uploadListing() {
      let formData = new FormData();
      this.image.forEach((img, i) => formData.append(`image${i + 1}`, img));
      console.log(formData)
      formData.append('name', this.locationName.nativeElement.value);
      formData.append('description', this.locationDescription.nativeElement.value);
      formData.append('neighborhoodOverview', this.locationNeighborhood.nativeElement.value);
      formData.append('longitude', this.locationLongitude.nativeElement.value);
      formData.append('latitude', this.locationLatitude.nativeElement.value);
      formData.append('location', this.locationUbication.nativeElement.value);
      formData.append('roomType', this.roomSelect.nativeElement.value);
      formData.append('propertyType', this.propertySelect.nativeElement.value);
      formData.append('capacity', this.locationCapacity.nativeElement.value);
      formData.append('bedrooms', this.locationBedrooms.nativeElement.value);
      formData.append('beds', this.locationBeds.nativeElement.value);
      formData.append('bathrooms', this.locationBathroom.nativeElement.value);
      formData.append('price', this.price.nativeElement.value);
      this.listServ.addUserListing(formData).subscribe((res) => {
         let emptyUser = Object.assign({});
         this.authServ.loggedUserData({...emptyUser, ...this.user}).subscribe((res) => this.user = res);
      });
   }

}
