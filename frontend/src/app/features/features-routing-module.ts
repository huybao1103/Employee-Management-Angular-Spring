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
import { authGuard } from '../core/auth-guard';
import { AccessDeniedComponent } from '../shared/components/access-denied-component/access-denied-component';
import { PoliciesKeys } from '../shared/const/policies-keys.const';

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
        path: 'access-denied',
        component: AccessDeniedComponent
      },
      {
        path: '',
        redirectTo: 'employees',
        pathMatch: 'full'
      },
      {
        path: 'employees',
        component: EmployeeListComponent,
        canActivate: [authGuard],
        data: { authorities: [PoliciesKeys.EmployeeView] }
      },
      {
        path: 'employees/:id/:view',
        component: ModalbaseComponent,
        outlet: 'modal',
        canActivate: [authGuard],
        data: { component: EmployeeForm, authorities: [PoliciesKeys.EmployeeView, PoliciesKeys.EmployeeUpdate] }
      },
      {
        path: 'departments',
        component: DepartmentListComponent,
        canActivate: [authGuard],
        data: { authorities: [PoliciesKeys.DepartmentView] }
      },
      {
        path: 'departments/:id/:view',
        component: ModalbaseComponent,
        outlet: 'modal',
        canActivate: [authGuard],
        data: { component: DepartmentForm, authorities: [PoliciesKeys.DepartmentView, PoliciesKeys.DepartmentUpdate] }
      },
      {
        path: 'users',
        component: UserListComponent,
        canActivate: [authGuard],
        data: { authorities: [PoliciesKeys.UserView] }
      },
      {
        path: 'users/:id/:view',
        component: ModalbaseComponent,
        outlet: 'modal',
        canActivate: [authGuard],
        data: { component: UserFormComponent, authorities: [PoliciesKeys.UserView, PoliciesKeys.UserUpdate] }
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeaturesRoutingModule { }
