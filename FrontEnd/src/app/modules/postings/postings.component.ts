import { Component } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';

@Component({
   selector: 'app-postings',
   templateUrl: './postings.component.html',
   styleUrls: ['./postings.component.scss']
})
export class PostingsComponent {

   image:File = Object.assign({});

   constructor(private userServ:UserService) { }


   getImage(e:Event) {
      const el = e.currentTarget as HTMLInputElement;
      let fileList:FileList|null = el.files;
      if (fileList) this.image = fileList[0];
   }

   uploadImage() {
      let formData = new FormData();
      formData.append('image', this.image);
      this.userServ.setUserPicture(formData).subscribe((res) => console.log(res))
   }
}
