import { ChangeDetectorRef, Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { EmployeeService } from '../services/employee.service';
import { IEmployeeListModel } from '../models/employee-list.model';
import { first } from 'rxjs';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './employee-list.html',
  styleUrls: ['./employee-list.scss'],
})
export class EmployeeListComponent implements OnInit {
  employees = signal<IEmployeeListModel[]>([]);
  loading = signal(false);
  error = signal('');

  constructor(
    private service: EmployeeService,
    private router: Router,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.service.list()
    .pipe(first())
    .subscribe({
      next: (emps) => {
        console.log(emps);
        
        this.employees.set(emps);
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
