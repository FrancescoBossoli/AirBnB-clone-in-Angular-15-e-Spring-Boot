import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TravelsRoutingModule } from './travels-routing.module';
import { TravelsComponent } from './travels.component';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { SliderComponent } from "../../shared/components/slider/slider.component";


@NgModule({
    declarations: [
        TravelsComponent
    ],
    imports: [
        CommonModule,
        TravelsRoutingModule,
        NgbPaginationModule,
        SliderComponent
    ]
})
export class TravelsModule { }
