import { HttpErrorResponse, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { Router } from "@angular/router";
import { catchError, switchMap, throwError } from "rxjs";
import { AuthService } from "../../features/auth/services/auth.service";
import { ToastsService } from "../../shared/components/toasts-message-component/services/toasts-service";


export const apiErrorInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  const toasts = inject(ToastsService);
  const router = inject(Router);
  const authService = inject(AuthService);

  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {

      // 403 → forbidden (no refresh)
      if (err.status === 403) {
        toasts.error(
          'You do not have permission for this action.',
          8000
        );
        router.navigate(['/login']);
        return throwError(() => err);
      }

      // 401 → try refresh (but not for refresh endpoint itself)
      if (err.status === 401 && !req.url.includes('/refresh')) {

        return authService.refreshToken().pipe(
          switchMap((newAccessToken: string | null) => {

            if (!newAccessToken) {
              router.navigate(['/login']);
              return throwError(() => err);
            }

            // Retry original request with new token
            const cloned = req.clone({
              setHeaders: {
                Authorization: `Bearer ${newAccessToken}`
              }
            });

            return next(cloned);
          }),
          catchError(refreshError => {
            router.navigate(['/login']);
            return throwError(() => refreshError);
          })
        );
      }

      // Other errors
      let message = 'An unexpected error occurred.';

      if (typeof err.error === 'string') {
        message = err.error;
      } else if (err.error?.message) {
        message = err.error.message;
      }

      toasts.error(message, 8000);

      return throwError(() => err);
    })
  );
};
