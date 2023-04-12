import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { of, switchMap } from 'rxjs';
import { JwtResponse } from 'src/app/core/interfaces/jwt-response.interface';
import { Review } from 'src/app/core/interfaces/review.inteface';
import { User } from 'src/app/core/interfaces/user.interface';
import { AuthService } from 'src/app/core/services/auth.service';
import { UserService } from 'src/app/core/services/user.service';
import { environment } from 'src/environments/environment.development';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

   user:User = Object.assign({});
   receivedReviews:Review[] = [];
   receivedReviewsPage:number = 1;
   ownReviewsPage:number = 1;
   image:File = Object.assign({});
   aboutEditing:boolean = false;
   locationEditing:boolean = false;
   @ViewChild('imgLabel') imgLabel!: ElementRef;
   @ViewChild('imgCheck') imgCheck!: ElementRef;
   @ViewChild('editAboutBtn') editAboutBtn!: ElementRef;
   @ViewChild('aboutBox') aboutBox!: ElementRef;
   @ViewChild('editLocationBtn') editLocationBtn!: ElementRef;
   @ViewChild('locationBox') locationBox!: ElementRef;

   get backEnd()     { return environment.backEnd }
   get profilePic()  {
                     if (this.user?.pictureUrl?.startsWith("BackEnd")) return environment.backEnd + this.user?.pictureUrl
                     return this.user?.pictureUrl + environment.mediumPic
                     }
   get userClass()   {
                     if (this.user.roles.includes('Administrator')) return 'Administrator';
                     else if (this.user.roles.includes('SuperHost')) return 'SuperHost';
                     else if (this.user.roles.includes('Host')) return 'Host';
                     else return 'User';
                     }
   get totalReceivedReviews() { return this.receivedReviews.length; }
   get totalWrittenReviews()  { return this.user.reviews.length }
   get totalReviews() { return this.totalReceivedReviews + this.totalWrittenReviews }


   constructor(private route: ActivatedRoute, private userServ:UserService, private authServ:AuthService) { }

   ngOnInit(): void {
      this.route.data.pipe(switchMap(data => of(data['user']))).subscribe((res) => {this.user = res; console.log(this.user)});
      if (this.user.listings.length > 0) {
         for (let i = 0; i < this.user.listings.length; i++) {
            for (let k = 0; k < this.user.listings[i].reviews.length; k++) {
               this.user.listings[i].reviews[k].score /=2;
               this.receivedReviews.push(this.user.listings[i].reviews[k]);
            }
         }
      }
      this.receivedReviews.sort((a,b) => {
         const aD = new Date(a.date);
         const bD = new Date(b.date);
         return aD.getTime() - bD.getTime();
      });
   }

   getImage(e:Event) {
      const el = e.currentTarget as HTMLInputElement;
      let fileList:FileList|null = el.files;
      if (fileList) this.image = fileList[0];
      this.imgLabel.nativeElement.classList.toggle('d-none');
      this.imgCheck.nativeElement.classList.toggle('d-none');
      this.uploadImage();
   }

   uploadImage() {
      let formData = new FormData();
      formData.append('image', this.image);
      this.userServ.setUserPicture(formData).subscribe((res) => {
         console.log(res);
         this.updateUser();
      });
   }

   updateUser() {
      const userData = localStorage.getItem('user');
      const jwt: JwtResponse = JSON.parse(userData!);
      this.authServ.loggedUserData(jwt).subscribe((res) => this.user = res);
      console.log(this.user);
   }

   editAbout() {
      if (this.aboutBox) {
         this.user.about = this.aboutBox.nativeElement.value;
         this.editUserInfo();
      }
      this.aboutEditing = !this.aboutEditing;
      this.editAboutBtn.nativeElement.classList.toggle('bg-primary');
      this.editAboutBtn.nativeElement.classList.toggle('bg-warning');
   }

   editLocation() {
      if (this.locationBox) {
         this.user.location = this.locationBox.nativeElement.value;
         this.editUserInfo();
      }
      this.locationEditing = !this.locationEditing;
      this.editLocationBtn.nativeElement.classList.toggle('bg-primary');
      this.editLocationBtn.nativeElement.classList.toggle('bg-warning');
   }

   editUserInfo() {
      this.userServ.editUser(this.user).subscribe((res) => this.updateUser());
   }


}
