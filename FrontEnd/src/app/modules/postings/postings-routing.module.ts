import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PostingsComponent } from './postings.component';

const routes: Routes = [{ path: '', component: PostingsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PostingsRoutingModule { }
