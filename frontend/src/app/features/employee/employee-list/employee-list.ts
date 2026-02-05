import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { EmployeeService } from '../services/employee.service';
import { EmployeeListModel } from '../models/employee-list.model';
import { first } from 'rxjs';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './employee-list.html',
  styleUrls: ['./employee-list.scss'],
})
export class EmployeeListComponent implements OnInit {
  employees: EmployeeListModel[] = [];
  loading = signal(false);
  error = signal('');

  constructor(private service: EmployeeService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.service.list()
    .pipe(first())
    .subscribe({
      next: (emps) => {
        this.employees = emps;
        this.loading.set(true);
      },
      error: (err) => {
        this.error.set(err?.message || 'Failed to load employees');
        this.loading.set(false);
      }
    });
  }

  salary(emp: EmployeeListModel): string {
    const s = emp.salary;
    if (typeof s === 'number') return s.toLocaleString();
    return s ?? '-';
  }
}
