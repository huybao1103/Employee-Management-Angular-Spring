import { Component, inject, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';
import { first } from 'rxjs';
import { DepartmentService } from '../services/department-service';

@Component({
  selector: 'app-department-list',
  imports: [],
  templateUrl: './department-list.html',
  styleUrl: './department-list.scss',
})
export class DepartmentListComponent implements OnInit {
  departmentService = inject(DepartmentService);
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
    this.departmentService.list()
    .pipe(first())
    .subscribe({
      next: (deps) => {
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set(err?.message || 'Failed to load departments');
        this.loading.set(false);
      }
    });
  }

  editDepartment(id: string, view?: boolean): void {
    this.router.navigate([{ outlets: { modal: ['departments', id, view ?? false] } }]);
  }
}
