import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, catchError, filter, first, map, Observable, of, take, tap } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';
import { LoginRequestModel } from '../models/LoginRequestModel';
import { LoginResponseModel } from '../models/LoginResponseModel';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly api = inject(ApiService);
  private readonly tokenKey = 'access_token';
  private readonly resource = 'api/auth';

  private _refreshInProgress = false;
  private _refreshSubject = new BehaviorSubject<string | null>(null);
  
  login(req: LoginRequestModel): Observable<string | null> {
    return this.api.post<LoginResponseModel>(`${this.resource}/login`, req)
    .pipe(
      first(),
      map(res => {
        const accessToken = res.accessToken;
        if (accessToken) {
          this.setAccessToken(accessToken);
          return null;
        } else {
          return 'Login succeeded but token was not returned.';
        }
      })
    )
  }

  refreshToken(): Observable<string | null> {
    // Prvent multiple refreshes if more than one request fails simultaneously
    if (this._refreshInProgress) {
      return this._refreshSubject.pipe(
        filter(token => token !== null),
        take(1)
      );
    }

    this._refreshInProgress = true;

    return this.api.post<{ accessToken: string }>(
      `${this.resource}/refresh`,
      {}
    ).pipe(
      tap(response => {
        this.setAccessToken(response.accessToken);
        this._refreshInProgress = false;
        this._refreshSubject.next(response.accessToken);
      }),
      map(res => res.accessToken),
      catchError(err => {
        this._refreshInProgress = false;
        this._refreshSubject.next(null);
        return of(null);
      })
    );
  }

  logout(): Observable<string | null> {
    return this.api.post<LoginResponseModel>(`${this.resource}/logout`, {})
    .pipe(
      first(),
      map(res => {
        this.clear();
        return 'Logged out successfully.';
      })
    )
  }
  getAccessToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  setAccessToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  clear(): void {
    localStorage.removeItem(this.tokenKey);
  }
}
