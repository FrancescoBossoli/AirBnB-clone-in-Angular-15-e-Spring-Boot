import { listingResolver } from './../../core/resolvers/listing.resolver';
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
   isLogged:boolean = false;
   lat = 0;
   minLat = 0;
   maxLat = 0;
   long = 0;
   minLong = 0;
   maxLong = 0;

   map!: mapboxgl.Map;
   style = 'mapbox://styles/mapbox/streets-v11';
   markerCollection:mapboxgl.Marker[] = [];

   constructor(private route: ActivatedRoute, private listServ: ListingService, private authSrv: AuthService) { }

   ngOnInit(): void {
      this.authSrv.user$.subscribe((res) => this.user = {...this.emptyUser, ...res} );
      this.authSrv.isLoggedIn$.subscribe((res) => this.isLogged = res);
      this.resetMap();
      if (window.location.pathname == '/results') {
         this.listServ.results$.pipe(delay(0)).subscribe((res) => {
            this.listings = res;
            this.resetMap();
            res.forEach((listing) => {
               // this.listings.push(listing)
               this.lat += listing.latitude;
               this.long += listing.longitude;
               this.setMarker(listing);
            })
            this.map.setCenter([this.long/this.listings.length, this.lat/this.listings.length]);
            this.setZoom();
         });
      } else {
         this.listings = this.user.favourites;
         this.listings.forEach((listing) => {
            this.lat += listing.latitude;
            this.long += listing.longitude;
            this.setMarker(listing);
         })
         this.map.setCenter([this.long/this.listings.length, this.lat/this.listings.length]);
         this.setZoom();
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
      .setPopup(new mapboxgl.Popup({focusAfterOpen: false, anchor:'center'}).setHTML("<div style='width:160px;'><div class='ratio ratio-4x3'><a href=\"/listing/" + listing.id + "\" class='ratio ratio-4x3'><img src='" + listing.pictures[0] + "' class='rounded-4'></a></div><p class='text-truncate text-black mb-0'>" + listing.name + "</p><div class='d-flex justify-content-between'><p class='mb-0 text-black'><span class='fw-semibold'>€" + listing.price + "</span>/notte</p><p class='me-1 mb-0 text-black'><i class='fa-sharp fa-solid fa-star-sharp'></i>" + (avg/listing.reviews.length).toFixed(2) + "</p></div></div>"))
      .addTo(this.map);
      this.markerCollection.push(marker);

      const el = document.createElement('div');
      el.className = 'marker';
      el.style.backgroundColor = '#7A88EC';
      el.style.width = '38px';
      el.style.height = '38px';
      el.style.borderRadius = '50%';
      el.style.color = '#fff';
      el.style.textAlign = 'center';
      el.style.fontSize = '14px';
      el.style.lineHeight = '38px';
      el.textContent = listing.price.toString() + '€';
      marker.getElement().firstChild?.replaceWith(el);
   }

   setZoom() {
      this.minLat = this.markerCollection[0].getLngLat().lat;
      this.maxLat = this.markerCollection[0].getLngLat().lat;
      this.minLong = this.markerCollection[0].getLngLat().lng;
      this.maxLong = this.markerCollection[0].getLngLat().lng;
      for (let x = 1; x < this.markerCollection.length; x++) {
         if (this.markerCollection[x].getLngLat().lat > this.maxLat) this.maxLat = this.markerCollection[x].getLngLat().lat;
         if (this.markerCollection[x].getLngLat().lat < this.minLat) this.minLat = this.markerCollection[x].getLngLat().lat;
         if (this.markerCollection[x].getLngLat().lng > this.maxLong) this.maxLong = this.markerCollection[x].getLngLat().lng;
         if (this.markerCollection[x].getLngLat().lng < this.minLong) this.minLong = this.markerCollection[x].getLngLat().lng;
      }
      this.map.fitBounds([new mapboxgl.LngLat(this.minLong, this.minLat),new mapboxgl.LngLat(this.maxLong, this.maxLat)], {padding:80});
      this.map.on('click', (e) => this.map.setCenter(e.lngLat))
   }

   resetMap() {
      this.lat = 0;
      this.long = 0;
      this.minLat = 0;
      this.maxLat = 0;
      this.minLong = 0;
      this.maxLong = 0;

      this.map = new mapboxgl.Map({
         accessToken: environment.mapToken,
         container: 'map',
         style: this.style,
         zoom: 13
      });
      this.markerCollection.forEach((marker) => marker.remove());
   }


}
