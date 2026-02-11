import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from '../shared/components/navbar-component/navbar-component';

@Component({
  selector: 'app-features.component',
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './features.component.html',
  styles: `
    .side-bar-panel {
      max-width: 100px;
    }
  `,
})
export class FeaturesComponent {
}
