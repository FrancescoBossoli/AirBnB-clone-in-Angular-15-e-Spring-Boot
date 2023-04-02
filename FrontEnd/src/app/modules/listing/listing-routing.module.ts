import { listingResolver } from './../../core/resolvers/listing.resolver';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListingComponent } from './listing.component';

const routes: Routes = [{ path: '', component: ListingComponent, resolve: {listing: listingResolver}}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ListingRoutingModule { }
