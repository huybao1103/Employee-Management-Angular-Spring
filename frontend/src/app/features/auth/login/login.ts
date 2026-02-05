import { CommonModule } from '@angular/common';
import { Component, effect, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { first } from 'rxjs';
import { LoginRequestModel } from '../models/LoginRequestModel';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss'],
})
export class LoginComponent {
  private fb = new FormBuilder();

  form = this.fb.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  loading = signal(false);
  error = signal('');

  constructor(
    private auth: AuthService,
    private router: Router
  ) {
  }

  submit(): void {
    if (this.form.invalid) return;
    this.error.set('');
    this.loading.set(true);

    const payload = this.form.value as LoginRequestModel;

    this.auth.login(payload)
      .pipe(first())
      .subscribe({
        next: (errMsg) => {
          this.loading.set(false);
          if (!errMsg) {
            this.router.navigate(['/employees']);
          } else {
            this.error.set(errMsg);
          }
        },
        error: (err) => {
          this.loading.set(false);
          const errorMsg = err?.error?.message || err?.message || 'Login failed';
          this.error.set(errorMsg);
        }
      });
  }
}
