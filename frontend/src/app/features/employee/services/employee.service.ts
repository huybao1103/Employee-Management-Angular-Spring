import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';
import { EmployeeListModel } from '../models/employee-list.model';


@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private readonly api = inject(ApiService);
  private readonly resource = 'api/employees';

  list(): Observable<EmployeeListModel[]> {
    return this.api.get<EmployeeListModel[]>(this.resource);
  }

  get(id: number | string): Observable<EmployeeListModel> {
    return this.api.get<EmployeeListModel>(`${this.resource}/${id}`);
  }

  create(data: Partial<EmployeeListModel>): Observable<EmployeeListModel> {
    return this.api.post<EmployeeListModel>(this.resource, data);
  }

  update(id: number | string, data: Partial<EmployeeListModel>): Observable<EmployeeListModel> {
    return this.api.put<EmployeeListModel>(`${this.resource}/${id}`, data);
  }

  remove(id: number | string): Observable<void> {
    return this.api.delete<void>(`${this.resource}/${id}`);
  }
}
