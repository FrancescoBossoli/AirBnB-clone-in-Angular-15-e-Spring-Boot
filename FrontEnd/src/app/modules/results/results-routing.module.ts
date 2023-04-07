import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResultsComponent } from './results.component';
import { resultsResolver } from 'src/app/core/resolvers/results.resolver';

const routes: Routes = [{ path: '', component: ResultsComponent, resolve: {listings: resultsResolver} }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ResultsRoutingModule { }
