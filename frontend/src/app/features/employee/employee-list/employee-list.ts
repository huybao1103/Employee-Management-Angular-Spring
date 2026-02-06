import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { first } from 'rxjs';
import { IEmployeeListModel } from '../models/employee-list.model';
import { EmployeeService } from '../services/employee.service';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './employee-list.html',
  styleUrls: ['./employee-list.scss'],
})
export class EmployeeListComponent implements OnInit {
  employeeService = inject(EmployeeService);
  loading = signal(false);
  error = signal('');
  
  constructor(
    private router: Router
  ) {}
  
  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.employeeService.list()
    .pipe(first())
    .subscribe({
      next: (emps) => {
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set(err?.message || 'Failed to load employees');
        this.loading.set(false);
      }
    });
  }

  salary(emp: IEmployeeListModel): string {
    const s = emp.salary;
    if (typeof s === 'number') return s.toLocaleString();
    return s ?? '-';
  }

  editEmployee(id: string | undefined): void {
    this.router.navigate([{ outlets: { modal: ['employees', id] } }]);
  }
}
