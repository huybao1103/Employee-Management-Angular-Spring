import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ToastsMessageComponent } from "./shared/components/toasts-message-component/toasts-message-component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ToastsMessageComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('frontend');
}
