import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule, Route } from '@angular/router';
import { first, switchMap } from 'rxjs';
import { EmployeeService } from '../services/employee.service';
import { IEditEmployeeModel } from '../models/edit-employee.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IDialogType } from '../../../shared/components/modal-base-component/modal-base-component';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './employee-form.html',
  styleUrls: ['./employee-form.scss'],
})
export class EmployeeForm implements OnInit, IDialogType {
  form: any;

  loading = signal(false);
  error = signal('');

  constructor(
    private fb: FormBuilder,
    private service: EmployeeService,
    private router: Router,
    private route: ActivatedRoute,
    private modal: NgbActiveModal,
  ) {
    this.form = this.fb.group({
      id: [''],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      salary: [0, [Validators.required, Validators.min(0)]],
      department: ['', Validators.required]
    });
  }

  dialogInit(para: { id: string }): void {
    if(para && para.id) {
      this.form.patchValue({ id: para.id });
      this.load(para.id);
    }
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
  }

  load(id: string | number): void {
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
