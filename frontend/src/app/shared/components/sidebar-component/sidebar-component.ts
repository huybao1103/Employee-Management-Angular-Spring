import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgbActiveOffcanvas } from '@ng-bootstrap/ng-bootstrap';
import { bootstrapBuildingsFill, bootstrapPeopleFill } from '@ng-icons/bootstrap-icons';
import { NgIcon, provideIcons } from "@ng-icons/core";

export interface IMenuItem {
  label: string;
  route: string;
  icon: string;
}

@Component({
  selector: 'app-sidebar-component',
  templateUrl: './sidebar-component.html',
  styleUrls: ['./sidebar-component.scss'],
  imports: [RouterModule, NgIcon],
  providers: [provideIcons({ 
    bootstrapPeopleFill,
    bootstrapBuildingsFill  
  })],
})
export class SidebarComponent {
  menuItems: IMenuItem[] = [
    {
      label: 'Employees',
      route: '/employees',
      icon: 'bootstrapPeopleFill'
    },
    {
      label: 'Departments',
      route: '/departments',
      icon: 'bootstrapBuildingsFill'
    },
  ];

  constructor(public activeOffcanvas: NgbActiveOffcanvas) {}
}
