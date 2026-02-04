import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FeaturesRoutingModule } from './features-routing-module';
import { ToastsMessageComponent } from '../shared/components/toasts-message-component/toasts-message-component';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    FeaturesRoutingModule,
    ToastsMessageComponent
  ]
})
export class FeaturesModule { }
