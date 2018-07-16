import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from "../../shared/services/auth/auth.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {

  @Input("actualVersion")
  actualVersion: string = "";

  constructor(private authService: AuthService) {
  }

  isLogged(): boolean {
    return this.authService.isLogged();
  }

  logout() {
    this.authService.logout();
  }
}
