import {Component, Input} from '@angular/core';
import {AuthService} from '../../shared/services/auth/auth.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {

  @Input('actualVersion')
  actualVersion = '';

  constructor(private authService: AuthService) {
  }

  isLogged(): boolean {
    return this.authService.isLogged();
  }

  logout() {
    this.authService.logout();
  }
}
