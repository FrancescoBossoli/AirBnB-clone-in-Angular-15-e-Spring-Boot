import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { of, switchMap } from 'rxjs';
import { User } from 'src/app/core/interfaces/user.interface';
import { environment } from 'src/environments/environment.development';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

   user:User = Object.assign({});

   get profilePic() { return this.user?.pictureUrl + environment.mediumPic; }

   constructor(private route: ActivatedRoute ) { }

   ngOnInit(): void {
      this.route.data.pipe(switchMap(data => of(data['user']))).subscribe((res) => this.user = res);
   }



}
