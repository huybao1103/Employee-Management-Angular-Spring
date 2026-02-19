import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ModalbaseComponent } from '../shared/components/modal-base-component/modal-base-component';
import { EmployeeForm } from './employee/employee-form/employee-form';
import { EmployeeListComponent } from './employee/employee-list/employee-list';
import { FeaturesComponent } from './features.component';
import { DepartmentForm } from './department/department-form/department-form';
import { DepartmentListComponent } from './department/department-list/department-list';
import { UserListComponent } from './user/user-list/user-list';
import { UserFormComponent } from './user/user-form/user-form';

const routes: Routes = [
  {
    path: '',
    redirectTo: '',
    pathMatch: 'full'
  },
  {
    path: '',
    component: FeaturesComponent,
    children: [
      {
        path: '',
        redirectTo: 'employees',
        pathMatch: 'full'
      },
      {
        path: 'employees',
        component: EmployeeListComponent
      },
      {
        path: 'employees/:id/:view',
        component: ModalbaseComponent,
        outlet: 'modal',
        data: { component: EmployeeForm }
      },
      {
        path: 'departments',
        component: DepartmentListComponent
      },
      {
        path: 'departments/:id/:view',
        component: ModalbaseComponent,
        outlet: 'modal',
        data: { component: DepartmentForm }
      },
      {
        path: 'users',
        component: UserListComponent
      },
      {
        path: 'users/:id/:view',
        component: ModalbaseComponent,
        outlet: 'modal',
        data: { component: UserFormComponent }
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeaturesRoutingModule { }
