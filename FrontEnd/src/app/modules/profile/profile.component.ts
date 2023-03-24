import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { of, switchMap } from 'rxjs';
import { Review } from 'src/app/core/interfaces/review.inteface';
import { User } from 'src/app/core/interfaces/user.interface';
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

   get profilePic()  { return this.user?.pictureUrl + environment.mediumPic }
   get userClass()   {
                     if (this.user.roles.includes('Administrator')) return 'Administrator';
                     else if (this.user.roles.includes('SuperHost')) return 'SuperHost';
                     else if (this.user.roles.includes('Host')) return 'Host';
                     else return 'User';
                     }
   get totalReceivedReviews() { return this.receivedReviews.length; }
   get totalWrittenReviews()  { return this.user.reviews.length }
   get totalReviews() { return this.totalReceivedReviews + this.totalWrittenReviews }


   constructor(private route: ActivatedRoute ) { }

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





}
