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
      console.log(this.listing)
      this.isFavourite = !this.isFavourite;
      console.log(this.favourites)
   }
}
