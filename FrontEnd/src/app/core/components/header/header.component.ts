import { Router } from '@angular/router';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { User } from '../../interfaces/user.interface';
import { environment } from 'src/environments/environment.development';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

   emptyUser:User = Object.assign({});
   user:User = Object.assign({});
   darkMode:boolean = false;
   route = this.router.url;

   get avatar() { return this.user?.pictureUrl + environment.thumbnail; }

   constructor(private authServ: AuthService, private router: Router) { }

   ngOnInit(): void {
      this.authServ.user$.subscribe({ next: (user) => { this.user = {...this.emptyUser, ...user} }})
   }

   logout():void {
      this.authServ.logout();
   }

   switchTheme() {
      document.body.classList.toggle("darkMode");
      document.body.classList.toggle("lightMode");
      this.darkMode = !this.darkMode;
      console.log(document.body)
   }

   openProfile() {
      this.router.navigate(['profile'])
   }

   openSettings() {
      this.router.navigate(['results'])
   }










}
