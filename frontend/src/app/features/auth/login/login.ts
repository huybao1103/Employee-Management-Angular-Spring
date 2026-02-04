import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { LoginRequestModel } from '../models/LoginRequestModel';
import { AuthService } from '../services/auth.service';
import { first } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss'],
})
export class LoginComponent {
  private fb = new FormBuilder();

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
  });

  loading: boolean = false;
  error: string = '';

  constructor(
    private auth: AuthService,
    private router: Router
  ) {
  }

  submit(): void {
    if (this.form.invalid) return;
    this.error = '';
    this.loading = true;

    const payload = this.form.value as LoginRequestModel;
    this.auth.login(payload)
    .pipe(first())
    .subscribe({
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
