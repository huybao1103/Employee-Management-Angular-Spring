import { inject, Injectable, signal } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Observable, tap } from 'rxjs';
import { IDepartmentModel } from '../models/department.model';
import { IOptionsModel } from '../../../shared/models/options.model';

@Injectable({
  providedIn: 'root',
})
export class DepartmentService {
  private readonly api = inject(ApiService);
  private readonly resource = 'api/departments';

  private readonly _departments = signal<IDepartmentModel[]>([]);
  readonly departments$ = this._departments.asReadonly();
  
  list(): Observable<IDepartmentModel[]> {
    return this.api.get<IDepartmentModel[]>(this.resource)
    .pipe(tap(emps => this._departments.set(emps)));
  }

  get(id: number | string): Observable<IDepartmentModel> {
    return this.api.get<IDepartmentModel>(`${this.resource}/${id}`);
  }

  create(data: IDepartmentModel) {
    return this.api.post<IDepartmentModel>(this.resource, data);
  }

  update(id: number | string, data: IDepartmentModel) {
    return this.api.put<IDepartmentModel>(`${this.resource}/${id}`, data);
  }

  remove(id: number | string): Observable<void> {
    return this.api.delete<void>(`${this.resource}/${id}`);
  }

  options(): Observable<IOptionsModel[]> {
    return this.api.get<IOptionsModel[]>(`${this.resource}/options`);
  }
}
