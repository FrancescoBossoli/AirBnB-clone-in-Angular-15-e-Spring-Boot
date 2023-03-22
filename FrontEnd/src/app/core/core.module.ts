import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { AuthComponent } from './components/auth/auth.component';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
   declarations: [
      HeaderComponent,
      FooterComponent,
      AuthComponent
   ],
   imports: [
      CommonModule,
      NgbDropdownModule,
      ReactiveFormsModule
   ],
   exports: [
      HeaderComponent,
      FooterComponent,
      AuthComponent
   ]
})
export class CoreModule { }
