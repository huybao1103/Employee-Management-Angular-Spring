import { Component, inject, signal } from '@angular/core';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { first } from 'rxjs';

@Component({
  selector: 'app-user-list',
  imports: [],
  templateUrl: './user-list.html',
  styleUrl: './user-list.scss',
})
export class UserListComponent {
  userService = inject(UserService);
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
    this.userService.list()
    .pipe(first())
    .subscribe({
      next: (emps) => {
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set(err?.message || 'Failed to load users');
        this.loading.set(false);
      }
    })
  }

  editUser(id: string, view?: boolean): void {
    this.router.navigate([{ outlets: { modal: ['users', id, view ?? false] } }]);
  }
}
