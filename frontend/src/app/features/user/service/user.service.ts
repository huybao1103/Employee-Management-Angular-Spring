import { inject, Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';
import { IUpdateUserModel } from '../models/update-user.model';
import { IUserListModel } from '../models/user-list.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly api = inject(ApiService);
  private readonly resource = 'api/users';

  private readonly _users = signal<IUserListModel[]>([]);
  readonly users$ = this._users.asReadonly();
  
  list(): Observable<IUserListModel[]> {
    return this.api.get<IUserListModel[]>(this.resource)
    .pipe(tap(emps => this._users.set(emps)));
  }

  get(id: number | string): Observable<IUserListModel> {
    return this.api.get<IUserListModel>(`${this.resource}/${id}`);
  }

  create(data: IUpdateUserModel) {
    return this.api.post<IUserListModel>(this.resource, data);
  }

  update(id: number | string, data: IUpdateUserModel) {
    return this.api.put<IUserListModel>(`${this.resource}/${id}`, data);
  }

  remove(id: number | string): Observable<void> {
    return this.api.delete<void>(`${this.resource}/${id}`);
  }
  
}
