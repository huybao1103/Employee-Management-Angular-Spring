import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
    {
      path: '',
      loadComponent: () => import('./auth/login/login').then(m => m.LoginComponent)
    },
    {
      path: 'employees',
      loadComponent: () => import('./employee/employee-list/employee-list').then(m => m.EmployeeListComponent)
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeaturesRoutingModule { }
