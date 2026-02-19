import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { first, switchMap } from 'rxjs';
import { IDialogType } from '../../../shared/components/modal-base-component/modal-base-component';
import { IOptionsModel } from '../../../shared/models/options.model';
import { DepartmentService } from '../../department/services/department-service';
import { IEditEmployeeModel } from '../models/edit-employee.model';
import { EmployeeService } from '../services/employee.service';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './employee-form.html',
  styleUrls: ['./employee-form.scss'],
})
export class EmployeeForm implements IDialogType {
  form: FormGroup;

  loading = signal(false);
  error = signal('');

  departmentOptions = signal<IOptionsModel[]>([]);

  constructor(
    private fb: FormBuilder,
    private service: EmployeeService,
    private modal: NgbActiveModal,
    private departmentService: DepartmentService,
  ) {
    this.form = this.fb.group({
      id: [''],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      salary: [0, [Validators.required, Validators.min(0)]],
      department_id: ['', Validators.required]
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
    this.loadDepartmentOptions();
  }

  load(id: string): void {
    this.loading.set(true);
    this.service.get(id)
      .pipe(first())
      .subscribe({
        next: (emp) => {
          this.form.patchValue(emp as IEditEmployeeModel);
          this.loading.set(false);
        },
        error: (err) => {
          this.error.set(err?.message || 'Failed to load employee');
          this.loading.set(false);
        }
      });
  }

  loadDepartmentOptions(): void {
    this.departmentService.options()
      .pipe(first())
      .subscribe({
        next: (options) => {
          this.departmentOptions.set(options);
        },
        error: (err) => {
          this.error.set(err?.message || 'Failed to load department options');
        }
      });
  }

  save(): void {
    if (this.form.invalid) return;
    this.loading.set(true);
    const data = this.form.value as IEditEmployeeModel;
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
