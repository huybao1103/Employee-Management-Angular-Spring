import { Injectable, signal } from '@angular/core';
import { ToastInfo, ToastType } from '../models/ToastInfo.model';

@Injectable({
  providedIn: 'root',
})
export class ToastsService {
  private readonly _toasts = signal<ToastInfo[]>([]);
	readonly toasts = this._toasts.asReadonly();

  show(toast: ToastInfo) {
    const withDefaults: ToastInfo = {
      type: toast.type ?? ToastType.Info,
      body: toast.body ?? '',
      delay: toast.delay ?? 5000,
    };
    this._toasts.update(toasts => [...toasts, withDefaults]);
  }
  
  success(body: string, delay?: number) {
    this.show({ type: ToastType.Success, body, delay });
  }

  error(body: string, delay?: number) {
    this.show({ type: ToastType.Error, body, delay: delay ?? 7000 });
  }

  remove(toast: ToastInfo) {
		this._toasts.update((toasts) => toasts.filter((t) => t !== toast));
	}
}