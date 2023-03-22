import { Component, TemplateRef, ViewEncapsulation, ViewChildren } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
   selector: 'app-auth',
   templateUrl: './auth.component.html',
   encapsulation: ViewEncapsulation.None,
   styleUrls: ['./auth.component.scss']
})
export class AuthComponent {

   loginForm = new FormGroup({
      username: new FormControl('', {nonNullable: true}),
      password: new FormControl('', {nonNullable: true})
   })

   signupForm = new FormGroup({
      username: new FormControl('', {nonNullable: true}),
      password: new FormControl('', {nonNullable: true}),
      name: new FormControl('', {nonNullable: true}),
      surname: new FormControl('', {nonNullable: true}),
      email: new FormControl('', {nonNullable: true}),
   })

   get user() { return this.loginForm.controls.username }
   get psw() { return this.loginForm.controls.password }
   get username() { return this.signupForm.controls.username }
   get password() { return this.signupForm.controls.password }
   get name() { return this.signupForm.controls.name }
   get surname() { return this.signupForm.controls.surname }
   get email() { return this.signupForm.controls.email }


   failedLoginAttempt: boolean = false;
   failedSignupAttempt: boolean = false;

   constructor(private modalServ: NgbModal, private authServ: AuthService) { }

   openModal(template: TemplateRef<any>, letter: string) {
      const animation = letter == "f" ? "fadeIn" : "rotate";
      this.modalServ.open(template, { centered: true, windowClass: animation });
   }

   async tryLogin() {
      if (this.loginForm.status != "INVALID") {
         this.authServ.login({username: this.user.value, password:this.psw.value}).subscribe({
            next: (res) => {
               this.loginForm.reset();
               this.failedLoginAttempt = false;
               this.modalServ.dismissAll();
            },
            error: (err) => this.failedLoginAttempt = true
         });
      }
      else this.failedLoginAttempt = true;
   }

   async trySignup() {
      console.log(this.signupForm)
      if (this.signupForm.status != "INVALID") {
         this.authServ.signup({
            username: this.username.value,
            password: this.password.value,
            name: this.name.value,
            surname: this.surname.value,
            email: this.email.value
         }).subscribe({
            next: (res) => {
               this.signupForm.reset();
               this.failedSignupAttempt = false;
               this.modalServ.dismissAll();
            },
            error: (err) => {
               this.failedSignupAttempt = true
               console.log(err)
            }
         });
      }
      else this.failedSignupAttempt = true;
   }

}
