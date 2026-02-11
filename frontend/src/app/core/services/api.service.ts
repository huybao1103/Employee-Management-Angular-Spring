import { HttpClient } from '@angular/common/http';
import { inject, Injectable, InjectionToken } from '@angular/core';
import { Observable, Subject } from 'rxjs';

export const API_BASE_URL = new InjectionToken<string>('API_BASE_URL');

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly http = inject(HttpClient);
  private readonly base = inject(API_BASE_URL, { optional: true }) ?? '';
  private _refresh$ = new Subject<void>();

  get<T>(path: string, options?: object): Observable<T> {
    return this.http.get<T>(this.buildUrl(path), options);
  }

  post<T>(path: string, body: unknown, options?: object): Observable<T> {
    return this.http.post<T>(this.buildUrl(path), body, {...options, withCredentials: true});
  }

  put<T>(path: string, body: unknown, options?: object): Observable<T> {
    return this.http.put<T>(this.buildUrl(path), body, {...options, withCredentials: true});
  }

  delete<T>(path: string, options?: object): Observable<T> {
    return this.http.delete<T>(this.buildUrl(path), options);
  }


  private buildUrl(path: string): string {
    if (!path) return this.base;
    if (/^https?:\/\//i.test(path)) return path;
    const prefix = this.base.replace(/\/$/, '');
    const suffix = path.replace(/^\//, '');
    return prefix ? `${prefix}/${suffix}` : suffix;
  }
}
