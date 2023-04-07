import { Injectable, inject } from '@angular/core';
import {
   Router,
   RouterStateSnapshot,
   ActivatedRouteSnapshot,
   ResolveFn,
   ActivatedRoute
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Listing } from '../interfaces/listing.interface';
import { ListingService } from '../services/listing.service';
import { ListingSearch } from '../interfaces/listing-search.interface';

export const resultsResolver: ResolveFn<Listing[]> = (route: ActivatedRouteSnapshot) => {
   const listSrv = inject(ListingService);
   const params = route.queryParamMap;
   let form:ListingSearch = {
      location:params.get('location') == null ? '' : params.get('location')!.replace('-', ', '),
      arrival:params.get('arrival') == null ? '' : params.get('arrival')!,
      departure:params.get('departure') == null ? '' : params.get('departure')!,
      people:Number(params.get('guests'))
   };
   return listSrv.listingSearch(form);

}
