import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../features/auth/services/auth.service';

function decodeBase64Url(str: string): string {
  str = str.replace(/-/g, '+').replace(/_/g, '/');
  while (str.length % 4) {
    str += '=';
  }
  try {
    return decodeURIComponent(
      Array.prototype.map
        .call(atob(str), (c: string) => {
          return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        })
        .join('')
    );
  } catch {
    return atob(str);
  }
}

function parseJwt(token: string): any | null {
  try {
    const parts = token.split('.');
    if (parts.length < 2) return null;
    const payload = parts[1];
    const json = decodeBase64Url(payload);
    return JSON.parse(json);
  } catch {
    return null;
  }
}

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const token = authService.getAccessToken();
  if (!token) {
    return router.parseUrl('/login');
  }

  const payload = parseJwt(token);
  if (!payload) {
    authService.clear();
    return router.parseUrl('/login');
  }

  const now = Math.floor(Date.now() / 1000);
  if (payload.exp && payload.exp < now) {
    authService.clear();
    return router.parseUrl('/login');
  }

  const required: string[] = route?.data?.['authorities'] ?? [];
  if (!required || required.length === 0) {
    return true;
  }

  const userAuthorities: string[] = Array.isArray(payload.authorities) ? payload.authorities : [];

  const hasAll = required.every(r => userAuthorities.includes(r));
  if (hasAll) return true;

  return router.parseUrl('/access-denied');
};
