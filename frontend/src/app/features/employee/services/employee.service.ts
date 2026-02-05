import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';
import { IEmployeeListModel } from '../models/employee-list.model';
import { IEditEmployeeModel } from '../models/edit-employee.model';


@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private readonly api = inject(ApiService);
  private readonly resource = 'api/employees';

  list(): Observable<IEmployeeListModel[]> {
    return this.api.get<IEmployeeListModel[]>(this.resource);
  }

  get(id: number | string): Observable<IEmployeeListModel> {
    return this.api.get<IEmployeeListModel>(`${this.resource}/${id}`);
  }

  create(data: IEditEmployeeModel) {
    return this.api.post<IEmployeeListModel>(this.resource, data);
  }

  update(id: number | string, data: IEditEmployeeModel) {
    return this.api.put<IEmployeeListModel>(`${this.resource}/${id}`, data);
  }

  remove(id: number | string): Observable<void> {
    return this.api.delete<void>(`${this.resource}/${id}`);
  }
}
