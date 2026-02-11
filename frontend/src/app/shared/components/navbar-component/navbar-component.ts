import { Component, inject } from '@angular/core';
import { NgbOffcanvas } from '@ng-bootstrap/ng-bootstrap';
import { SidebarComponent } from '../sidebar-component/sidebar-component';
import { Router } from '@angular/router';
import { AuthService } from '../../../features/auth/services/auth.service';
import { ToastsService } from '../toasts-message-component/services/toasts-service';
import { first } from 'rxjs';

@Component({
  selector: 'app-navbar-component',
  imports: [],
  templateUrl: './navbar-component.html',
  styleUrl: './navbar-component.scss',
})
export class NavbarComponent {
  private offcanvas = inject(NgbOffcanvas);
  private router = inject(Router);
  private auth = inject(AuthService);
  private toasts = inject(ToastsService);

  openSidebar() {
    this.offcanvas.open(SidebarComponent, {
      position: 'start', // left sidebar
      backdrop: true,
      scroll: false,
      panelClass: 'side-bar-panel'
    });
  }
  
  signout() {
    this.auth.logout()
    .pipe(first())
    .subscribe({
      next: (mess) => {
        this.toasts.success(mess ?? 'Logged out successfully.');
        this.router.navigate(['/login']);
      },
    });
  }
}
