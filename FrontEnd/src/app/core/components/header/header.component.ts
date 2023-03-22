import { AuthService } from './../../services/auth.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { User } from '../../interfaces/user.interface';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, AfterViewInit {

   user:User|null = null;

   constructor(private authServ: AuthService) {

   }

   ngOnInit(): void {
      this.authServ.user$.subscribe({
         next: (user) => this.user = user

      })
   }

   ngAfterViewInit(): void {
      console.log(this.user)
   }








}
