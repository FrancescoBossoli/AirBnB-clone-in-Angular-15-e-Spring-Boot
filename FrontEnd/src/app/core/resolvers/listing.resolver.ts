import { Listing } from './../interfaces/listing.interface';
import { ListingService } from './../services/listing.service';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveFn } from '@angular/router';


export const listingResolver: ResolveFn<Listing> = (route: ActivatedRouteSnapshot) => {
   const listSrv = inject(ListingService);
   const id = Number(route.paramMap.get('id'));
   return listSrv.getListing(id);
};
