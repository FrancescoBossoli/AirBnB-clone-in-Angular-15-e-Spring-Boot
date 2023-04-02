import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
   providedIn: 'root'
})
export class ModalService {

   private loginNeed:BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
   loginNeed$: Observable<boolean> = this.loginNeed.asObservable();

   constructor() { }

   loginRequestNeeded(res:boolean) {
      this.loginNeed.next(res);
   }
}
