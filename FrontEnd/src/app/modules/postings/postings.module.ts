import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PostingsRoutingModule } from './postings-routing.module';
import { PostingsComponent } from './postings.component';


@NgModule({
  declarations: [
    PostingsComponent
  ],
  imports: [
    CommonModule,
    PostingsRoutingModule
  ]
})
export class PostingsModule { }
