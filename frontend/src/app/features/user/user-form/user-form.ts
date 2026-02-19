import { Component, signal } from '@angular/core';
import { UserService } from '../service/user.service';
import { IUpdateUserModel } from '../models/update-user.model';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { first, switchMap } from 'rxjs';
import { IOptionsModel } from '../../../shared/models/options.model';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-user-form',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './user-form.html',
  styleUrl: './user-form.scss',
})
export class UserFormComponent {
  form: FormGroup;

  loading = signal(false);
  error = signal('');
  
  id = '';

  constructor(
    private fb: FormBuilder,
    private service: UserService,
    private modal: NgbActiveModal
  ) {
    this.form = this.fb.group({
      id: [''],
      userName: ['', Validators.required],
      role: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  dialogInit(para: { id: string, view: string }): void {
    if(para) {
      if(para.id) {
        this.id = para.id;
        this.load(para.id);
      }
      if(para.view === 'true') {
        this.form.disable();
      }
    }
  }

  load(id: string): void {
    this.loading.set(true);
    this.service.get(id)
      .pipe(first())
      .subscribe({
        next: (emp) => {
          this.form.patchValue(emp as IUpdateUserModel);
          this.loading.set(false);
        },
        error: (err) => {
          this.error.set(err?.message || 'Failed to load user');
          this.loading.set(false);
        }
      });
  }

  save(): void {
    if (this.form.invalid) return;
    this.loading.set(true);
    const data = this.form.value as IUpdateUserModel;
    const id = data.id;
    const obs = id ? this.service.update(id, data) : this.service.create(data);
    obs.pipe(
      switchMap(() => this.service.list()),
      first()
    )
    .subscribe({
      next: () => this.cancel(),
      error: (err: any) => {
        this.error.set(err?.message || 'Save failed');
        this.loading.set(false);
      }
    });
  }

  cancel(): void {
    this.modal.close();
  }


}
