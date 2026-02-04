import { HttpErrorResponse, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { catchError, throwError } from "rxjs";
import { ToastsService } from "../../shared/components/toasts-message-component/services/toasts-service";


export const apiErrorInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  const toasts = inject(ToastsService);
  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {
      return throwError(() => {
        let message = 'An unexpected error occurred.';
        try {
          if (err?.error) {
            if (typeof err.error === 'string') message = err.error;
            else if (err.error.message) message = err.error.message;
            else if (err.error?.detail) message = err.error.detail;
          } else if (err?.message) {
            message = err.message;
          }
        } catch (e) { }

        toasts.error(message, 8000);
        return message;
      });
    })
  );
};
