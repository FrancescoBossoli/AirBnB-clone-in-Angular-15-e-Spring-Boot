import { AuthService } from './../../core/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { Listing } from 'src/app/core/interfaces/listing.interface';
import { User } from 'src/app/core/interfaces/user.interface';
import { ListingService } from 'src/app/core/services/listing.service';

@Component({
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

   list:Listing[] = [];
   islands:Listing[] = [];
   trending:Listing[] = [];
   topOfTheWorld:Listing[] = [];
   amazingViews:Listing[] = [];
   castles:Listing[] = [];
   popularCities:Listing[] = [];
   ryokan:Listing[] = [];
   beach:Listing[] = [];
   amazingPools:Listing[] = [];
   treeHouses:Listing[] = [];
   isLogged:boolean = false;
   emptyUser:User = Object.assign({});
   user:User = Object.assign({});


   constructor(private authSrv:AuthService, private listSrv:ListingService) { }

   ngOnInit(): void {
      this.authSrv.isLoggedIn$.subscribe((res) => this.isLogged = res);
      if (this.isLogged == true) this.authSrv.user$.subscribe((res) => this.user = {...this.emptyUser, ...res} );
      this.listSrv.getIslands().subscribe((res) => {
         this.islands = res;
         this.list = this.islands;
      });
      this.listSrv.getTrending().subscribe((res) => this.trending = res);
      this.listSrv.getTreehouses().subscribe((res) => this.treeHouses = res);
      this.listSrv.getTopOfTheWorld().subscribe((res) => this.topOfTheWorld = res);
      this.listSrv.getAmazingViews().subscribe((res) => this.amazingViews = res);
      this.listSrv.getCastles().subscribe((res) => this.castles = res);
      this.listSrv.getIconicCities().subscribe((res) => this.popularCities = res);
      this.listSrv.getRyokans().subscribe((res) => this.ryokan = res);
      this.listSrv.getBeachs().subscribe((res) => this.beach = res);
      this.listSrv.getAmazingPools().subscribe((res) => this.amazingPools = res);
   }

   preference(choice:string) {
      switch(choice) {
         case 'islands' : this.list = this.islands;
            break;
         case 'trending' : this.list = this.trending;
            break;
         case 'topOfTheWorld' : this.list = this.topOfTheWorld;
            break;
         case 'amazingViews' : this.list = this.amazingViews;
            break;
         case 'castles' : this.list = this.castles;
            break;
         case 'popularCities' : this.list = this.popularCities;
            break;
         case 'ryokan' : this.list = this.ryokan;
            break;
         case 'beach' : this.list = this.beach;
            break;
         case 'amazingPools' : this.list = this.amazingPools;
            break;
         case 'treeHouses' : this.list = this.treeHouses;
            break;

      }
   }

}
