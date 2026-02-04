import { HttpErrorResponse, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { catchError, throwError } from "rxjs";
import { ToastsService } from "../../shared/components/toasts-message-component/services/toasts-service";


export const apiErrorInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
    const toasts = inject(ToastsService);
    return next(req).pipe(
      catchError((err: HttpErrorResponse) => {
        toasts.showErrorFromApi(err);
        return throwError(() => err);
      })
    );
};
