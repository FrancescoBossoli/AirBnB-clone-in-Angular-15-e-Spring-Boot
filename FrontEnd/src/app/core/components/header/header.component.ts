import { UserService } from './../../services/user.service';
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

   user:User|null = null;
   darkMode:boolean = false;

   get avatar() { return this.user?.pictureUrl + environment.thumbnail;

   }

   constructor(private authServ: AuthService, private userServ: UserService) {

   }

   ngOnInit(): void {
      this.authServ.user$.subscribe({
         next: (user) => this.user = user
      })
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










}
