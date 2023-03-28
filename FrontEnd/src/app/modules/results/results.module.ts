import { PreviewComponent } from 'src/app/shared/components/preview/preview.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ResultsRoutingModule } from './results-routing.module';
import { ResultsComponent } from './results.component';


@NgModule({
  declarations: [
    ResultsComponent
  ],
  imports: [
    CommonModule,
    PreviewComponent,
    ResultsRoutingModule
  ]
})
export class ResultsModule { }
