import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { EmployeeService } from '../services/employee.service';
import { EmployeeListModel } from '../models/employee-list.model';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './employee-list.html',
  styleUrls: ['./employee-list.scss'],
})
export class EmployeeListComponent implements OnInit {
  employees: EmployeeListModel[] = [];
  loading = false;
  error?: string;

  constructor(private service: EmployeeService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading = true;
    this.service.list().subscribe({
      next: (emps) => {
        this.employees = emps;
        this.loading = false;
      },
      error: (err) => {
        this.error = err?.message || 'Failed to load employees';
        this.loading = false;
      },
    });
  }

  salary(emp: EmployeeListModel): string {
    const s = emp.salary;
    if (typeof s === 'number') return s.toLocaleString();
    return s ?? '-';
  }
}
