import { tap } from 'rxjs';
import { User } from './../interfaces/user.interface';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';

@Injectable({
   providedIn: 'root'
})
export class UserService {

   constructor(private http: HttpClient) { }

   getUser(id: number) {
      return this.http.get<User>(`${environment.api}/user/${id}`)
   }

   setUserPicture(form:FormData) {
      return this.http.post(`${environment.api}/user`, form)
   }

   editUser(user:User) {
      return this.http.put(`${environment.api}/user/${user.id}`, user)
   }
}
