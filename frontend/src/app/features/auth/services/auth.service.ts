import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';
import { LoginRequestModel } from '../models/LoginRequestModel';
import { LoginResponseModel } from '../models/LoginResponseModel';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly api = inject(ApiService);
  private readonly tokenKey = 'jwt_token';
  
  login(req: LoginRequestModel): Observable<string | null> {
    return this.api.post<LoginResponseModel>('api/auth/login', req).pipe(
      map(res => {
        const token = res.token;
        if (token) {
          this.setToken(token);
          return null;
        } else {
          return 'Login succeeded but token was not returned.';
        }
      })
    )
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  clear(): void {
    localStorage.removeItem(this.tokenKey);
  }
}
