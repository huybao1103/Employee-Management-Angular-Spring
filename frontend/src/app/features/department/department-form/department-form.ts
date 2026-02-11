import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Route, RouterModule } from '@angular/router';
import { IDialogType } from '../../../shared/components/modal-base-component/modal-base-component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DepartmentService } from '../services/department-service';
import { IDepartmentModel } from '../models/department.model';
import { first, switchMap } from 'rxjs';

@Component({
  selector: 'app-department-form',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './department-form.html',
  styleUrl: './department-form.scss',
})
export class DepartmentForm implements IDialogType {
  form: FormGroup;

  loading = signal(false);
  error = signal('');

  constructor(
    private fb: FormBuilder,
    private modal: NgbActiveModal,
    private service: DepartmentService,
  ) {
    this.form = this.fb.group({
      id: [''],
      name: ['', Validators.required],
      email: ['', [Validators.email]],
      address: ['', [Validators.max(200)]]
    });
  }

  dialogInit(para: { id: string, view: string }): void {
    if(para) {
      if(para.id) {
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
          this.form.patchValue(emp as IDepartmentModel);
          this.loading.set(false);
        },
        error: (err) => {
          this.error.set(err?.message || 'Failed to load department');
          this.loading.set(false);
        }
      });
  }

  save(): void {
    if (this.form.invalid) return;
    this.loading.set(true);
    const data = this.form.value as IDepartmentModel;
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
