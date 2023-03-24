import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';
import { ProfileComponent } from './profile.component';
import { NgbPaginationModule, NgbRatingModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
   declarations: [
      ProfileComponent
   ],
   imports: [
      CommonModule,
      ProfileRoutingModule,
      NgbRatingModule,
      NgbPaginationModule
   ]
})
export class ProfileModule { }
