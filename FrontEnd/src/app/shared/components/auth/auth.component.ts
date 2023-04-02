import { ModalService } from '../../../core/services/modal.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit, TemplateRef, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
   selector: 'app-auth',
   standalone: true,
   templateUrl: './auth.component.html',
   encapsulation: ViewEncapsulation.None,
   styleUrls: ['./auth.component.scss'],
   imports: [
      CommonModule,
      ReactiveFormsModule
   ]
})

export class AuthComponent implements OnInit {

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
   userTaken: boolean = false;
   emailPresent: boolean = false;
   wrongUsername: boolean = false;
   loginNeeded:boolean = false;

   @ViewChild('login') login!:TemplateRef<any>;

   constructor(private modalServ: NgbModal, private authServ: AuthService, private modSrv:ModalService) { }

    ngOnInit(): void {
      this.modSrv.loginNeed$.subscribe((res) => {
         this.loginNeeded = res;
         if (this.loginNeeded == true) {
            this.modalServ.open(this.login, { centered: true, windowClass: 'fadeIn' });
            this.modSrv.loginRequestNeeded(false);
         }
      });

   }

   openModal(template: TemplateRef<any>, letter: string) {
      const animation = letter == "f" ? "fadeIn" : "rotate";
      this.modalServ.open(template, { centered: true, windowClass: animation });
   }

   async tryLogin() {
      this.wrongUsername = false;
      if (this.loginForm.status != "INVALID") {
         this.authServ.login({username: this.user.value, password:this.psw.value}).subscribe({
            next: (res) => {
               this.loginForm.reset();
               this.failedLoginAttempt = false;
               this.modalServ.dismissAll();
            },
            error: (err) => {
               this.failedLoginAttempt = true;
               switch(err.message) {
                  case "L'username non sembra essere associato a un Account esistente":
                     this.wrongUsername = true;
               }
            }
         });
      }
      else this.failedLoginAttempt = true;
   }

   async trySignup() {
      console.log(this.signupForm)
      this.userTaken = false;
      this.emailPresent = false;
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
            error: (err:Error) => {
               this.failedSignupAttempt = true
               switch(err.message) {
                  case "L'username è già stato scelto":
                     this.userTaken = true;
                     break;
                  case "L'e-mail risulta già associata ad un Account esistente":
                     this.emailPresent = true;
                     break;
               }
            }
         });
      }
      else this.failedSignupAttempt = true;
   }

}
