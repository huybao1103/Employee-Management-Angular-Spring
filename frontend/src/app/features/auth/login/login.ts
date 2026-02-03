import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { ApiService } from '../../../core/services/api.service';
import { AuthService } from '../services/auth.service';
import { LoginRequestModel } from '../models/LoginRequestModel';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss'],
})
export class Login {
  private fb = new FormBuilder();

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
  });

  loading = false;
  error?: string;

  constructor(
    private api: ApiService,
    private auth: AuthService,
    private router: Router
  ) {
  }

  submit(): void {
    if (this.form.invalid) return;
    this.error = undefined;
    this.loading = true;

    const payload = this.form.value as LoginRequestModel;
    this.auth.login(payload).subscribe({
      next: (errMsg) => {
        this.loading = false;
        if (!errMsg) {
          this.router.navigate(['/employees']);
        } else {
          this.error = errMsg;
        }
      },
      error: (err) => {
        this.loading = false;
        this.error = err?.error?.message || err?.message || 'Login failed';
      }
    })
  }
}
