import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { AuthComponent } from '../shared/components/auth/auth.component';
import { NgbDatepickerModule, NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
   declarations: [
      HeaderComponent,
      FooterComponent
   ],
   imports: [
      CommonModule,
      NgbDropdownModule,
      FormsModule,
      ReactiveFormsModule,
      RouterModule,
      NgbDatepickerModule,
      AuthComponent
   ],
   exports: [
      HeaderComponent,
      FooterComponent
   ]
})
export class CoreModule { }
