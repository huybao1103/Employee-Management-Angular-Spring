import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { apiErrorInterceptor } from './core/interceptors/api-error.interceptor';
import { jwtInterceptor } from './core/interceptors/jwt.interceptor';
import { API_BASE_URL } from './core/services/api.service';

export const appConfig: ApplicationConfig = {
  providers: [
    // provideZoneChangeDetection(),
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(withInterceptors([jwtInterceptor, apiErrorInterceptor])),
    { provide: API_BASE_URL, useValue: 'http://localhost:8080' }
  ]
};
