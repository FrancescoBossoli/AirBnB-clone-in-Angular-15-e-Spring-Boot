import { HomeRoutingModule } from './home-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { ShowcaseComponent } from 'src/app/shared/components/showcase/showcase.component';



@NgModule({
   declarations: [
      HomeComponent
   ],
   imports: [
      CommonModule,
      HomeRoutingModule,
      ShowcaseComponent
   ]
})
export class HomeModule { }
