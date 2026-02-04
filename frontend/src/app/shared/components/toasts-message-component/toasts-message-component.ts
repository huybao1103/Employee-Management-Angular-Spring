import { Component, inject } from '@angular/core';
import { NgbToast } from '@ng-bootstrap/ng-bootstrap';
import { ToastsService } from './services/toasts-service';

@Component({
  selector: 'app-toasts-message-component',
  imports: [NgbToast],
  templateUrl: './toasts-message-component.html',
  styleUrls: ['./toasts-message-component.scss'],
	host: { class: 'toast-container position-fixed top-0 end-0 p-3', style: 'z-index: 1200' }
})
export class ToastsMessageComponent {
  readonly toastsService = inject(ToastsService);
}
