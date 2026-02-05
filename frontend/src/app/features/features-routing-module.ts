import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ModalbaseComponent } from '../shared/components/modal-base-component/modal-base-component';
import { EmployeeForm } from './employee/employee-form/employee-form';

const routes: Routes = [
    {
      path: '',
      loadComponent: () => import('./auth/login/login').then(m => m.LoginComponent)
    },
    {
      path: 'employees',
      loadComponent: () => import('./employee/employee-list/employee-list').then(m => m.EmployeeListComponent)
    },
    {
      path: 'employees/:id',
      component: ModalbaseComponent,
      outlet: 'modal',
      data: { component: EmployeeForm }
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeaturesRoutingModule { }
