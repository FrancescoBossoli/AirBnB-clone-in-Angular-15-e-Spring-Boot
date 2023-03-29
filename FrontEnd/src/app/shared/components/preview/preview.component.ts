import { ListingService } from './../../../core/services/listing.service';
import { Listing } from './../../../core/interfaces/listing.interface';
import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-preview',
  templateUrl: './preview.component.html',
  styleUrls: ['./preview.component.scss'],
  standalone: true,
  imports: [CommonModule]
})
export class PreviewComponent implements OnInit {

constructor(private listServ: ListingService) {}

   ngOnInit(): void {
      if (this.favourites != null)
      this.favourites.forEach((fav) => {if (fav.id == this.listing.id) this.isFavourite = true});
   }

   @ViewChild('favourite') favourite!: ElementRef;

   @Input()
   listing:Listing = Object.assign({});
   @Input()
   favourites:Listing[] = [];
   outputFormat:String = '?im_w=480';
   isFavourite:boolean = false;

   get averageVote():number {
      let avg = 0;
      this.listing.reviews.forEach((e) => avg += e.score);
      return avg/this.listing.reviews.length;
   }

   setFavourite() {
      this.isFavourite = !this.isFavourite;
      let check:boolean = false;
      this.favourites.forEach((favourite) => {
         if (favourite.id == this.listing.id) check = true
      });
      if (check) this.listServ.removeFavourite(this.listing.id).subscribe(() => this.favourites.splice(this.favourites.indexOf(this.listing),1));
      else this.listServ.setFavourite(this.listing.id).subscribe(() => this.favourites.push(this.listing));
      console.log(this.favourites)
   }
}
