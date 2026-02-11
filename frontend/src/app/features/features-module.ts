import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ToastsMessageComponent } from '../shared/components/toasts-message-component/toasts-message-component';
import { FeaturesRoutingModule } from './features-routing-module';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    FeaturesRoutingModule,
    ToastsMessageComponent
  ]
})
export class FeaturesModule { }
