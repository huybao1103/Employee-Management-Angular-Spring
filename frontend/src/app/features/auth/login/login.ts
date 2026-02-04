import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { LoginRequestModel } from '../models/LoginRequestModel';
import { AuthService } from '../services/auth.service';
import { first, finalize } from 'rxjs';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss'],
})
export class LoginComponent implements OnInit {
  private fb = new FormBuilder();

  form = this.fb.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  loading: boolean = false;
  error: string = '';

  constructor(
    private auth: AuthService,
    private router: Router
  ) {
  }

  private cd = inject(ChangeDetectorRef);

  ngOnInit(): void {
    
  }

  submit(): void {
    if (this.form.invalid) return;
    this.error = '';
    this.loading = true;

    const payload = this.form.value as LoginRequestModel;
    let errorMsg = '';

    this.auth.login(payload)
      .pipe(
        first(),
        finalize(() => {
          this.loading = false;
          if (errorMsg) this.error = errorMsg;
          this.cd.detectChanges();
        })
      )
      .subscribe({
        next: (errMsg) => {
          if (!errMsg) {
            this.router.navigate(['/employees']);
          } else {
            errorMsg = errMsg;
          }
        },
        error: (err) => {
          errorMsg = err?.error?.message || err?.message || 'Login failed';
        }
      });
  }
}
