import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from '../../shared/services/auth/auth.service';

@Component({
  selector: 'app-menu-mobile-items',
  templateUrl: './menu-mobile-items.component.html',
  styleUrls: ['./menu-mobile-items.component.css']
})
export class MenuMobileItemsComponent implements OnInit {

  @Input('sidebar')
  sidebar: any;

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  logout() {
    this.sidebar.toggle();
    this.authService.logout();
  }
}
