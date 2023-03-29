import { switchMap, of, delay, startWith } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/core/interfaces/user.interface';
import { AuthService } from './../../core/services/auth.service';
import { Listing } from './../../core/interfaces/listing.interface';
import { ListingService } from './../../core/services/listing.service';
import { Component, OnInit } from '@angular/core';
import * as mapboxgl from 'mapbox-gl';
import { environment } from 'src/environments/environment.development';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.scss']
})
export class ResultsComponent implements OnInit{

   listings:Listing[] = [];
   emptyUser:User = Object.assign({});
   user:User = Object.assign({});

   map!: mapboxgl.Map;
   style = 'mapbox://styles/mapbox/streets-v11';

   constructor(private route: ActivatedRoute, private listServ: ListingService) { }

   ngOnInit(): void {
      this.route.data.pipe(switchMap(data => of(data['user']))).subscribe((res) => {this.user = res; console.log(this.user)});
      console.log(window.location.pathname)
      let i = 0;
      let lat = 0;
      let long = 0;
      this.map = new mapboxgl.Map({
         accessToken: environment.mapToken,
         container: 'map',
         style: this.style,
         zoom: 13
      });
      if (window.location.pathname == '/results') {
         this.listServ.getAll().pipe(delay(0)).subscribe((res) => {
            if (res!= null)
            res.forEach((listing) => {
               this.listings.push(listing)
               lat += listing.latitude;
               long += listing.longitude;
               this.setMarker(listing);
            })
            this.map.setCenter([long/this.listings.length, lat/this.listings.length]);
         });
      } else {
         this.listings = this.user.favourites;
         this.listings.forEach((listing) => {
            lat += listing.latitude;
            long += listing.longitude;
            this.setMarker(listing);
         })
         this.map.setCenter([long/this.listings.length, lat/this.listings.length]);
      }
      this.map.on('click', (e) => this.map.setCenter(e.lngLat))
   }

   setMarker(listing:Listing) {
      let avg = 0;
      listing.reviews.forEach((e) => avg += e.score);
      const marker = new mapboxgl.Marker({
         draggable:false,
         color:'#7A88EC'
      })
      .setLngLat([listing.longitude, listing.latitude])
      .setPopup(new mapboxgl.Popup({focusAfterOpen: false, anchor:'center'}).setHTML("<div style='width:160px;'><div class='ratio ratio-4x3'><img src='" + listing.pictures[0] + "' class='rounded-4'></div><p class='text-truncate mb-0'>" + listing.name + "</p><div class='d-flex justify-content-between'><p class='mb-0'><span class='fw-semibold'>€" + listing.price + "</span>/notte</p><p class='me-1 mb-0'><i class='fa-sharp fa-solid fa-star-sharp'></i>" + (avg/listing.reviews.length).toFixed(2) + "</p></div></div>"))
      .addTo(this.map);

      const el = document.createElement('div');
      el.className = 'marker';
      el.style.backgroundColor = '#7A88EC';
      el.style.width = '30px';
      el.style.height = '30px';
      el.style.borderRadius = '50%';
      el.style.color = '#fff';
      el.style.textAlign = 'center';
      el.style.fontSize = '14px';
      el.style.lineHeight = '30px';
      el.textContent = listing.price.toString();
      marker.getElement().firstChild?.replaceWith(el);
   }


}