import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { Component, TemplateRef, ViewEncapsulation } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
   selector: 'ngbd-modal-signup',
   standalone: true,
   imports: [
      CommonModule,
      ReactiveFormsModule
   ],
   templateUrl: './signup.component.html',
   encapsulation: ViewEncapsulation.None,
   styleUrls: ['./signup.component.scss']
})
export class SignupComponent {

   constructor(private modalServ: NgbModal) { }

   openSignup(template: TemplateRef<any>) {
      this.modalServ.open(template, { centered: true, windowClass: "modalsize" });
   }

}
