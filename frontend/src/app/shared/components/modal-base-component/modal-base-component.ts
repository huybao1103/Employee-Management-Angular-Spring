import { Component, OnDestroy } from '@angular/core';
import { ParamMap, Route, Router, ActivatedRoute } from '@angular/router';
import { NgbModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { get } from 'lodash';
import { Subscription } from 'rxjs';

export declare interface IDialogType {
  uniqueId?: string;
  width?: string,
  height?: string
  size?: 'sm' | 'lg' | 'xl';

  dialogInit(para: any | undefined, routeConfig?: Route): void;

}

@Component({
  selector: 'app-modalbase',
  standalone: true,
  imports: [
    NgbModalModule
  ],
  templateUrl: './modal-base-component.html',
  styleUrls: ['./modal-base-component.scss']
})
export class ModalbaseComponent implements OnDestroy {
  subcription!: Subscription;
  outsideUrl: string = '/';
  component: any
  componentInstance: any;
  para: ParamMap | undefined;
  routeConfig!: Route | null;
  witdth: string | undefined;
  size?: 'sm' | 'lg' | 'xl';
  disableClose: boolean = false;
  constructor(
    private modalService: NgbModal,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.activatedRoute.data.subscribe({
      next: (data) => {
        this.component = get(data, 'component');
        this.routeConfig = get(activatedRoute.snapshot, 'routeConfig');
        this.para = get(activatedRoute.snapshot.paramMap, 'params', undefined);
        this.openDialog();
      },
      error: (err) => {
        console.log(err)
      }
    });
  }

  ngOnDestroy() {
    if(this.subcription)
      this.subcription.unsubscribe();
  }

  openDialog() {
    if (!this.component) return;
    const dialogRef = this.modalService.open(this.component, {
      centered: true,
      size: this.size ?? 'lg'
    });
    this.componentInstance = dialogRef.componentInstance;
    this.componentInstance.dialogInit(this.para, this.routeConfig);
    this.subcription = dialogRef.hidden
      .subscribe({
        next: () => {
          this.router.navigate([{ outlets: { modal: null } }], { relativeTo: this.activatedRoute.parent });
        }
      })
  }
}