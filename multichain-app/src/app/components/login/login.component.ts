import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../shared/services/auth/auth.service';
import {LoginData} from '../../shared/model/LoginData';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginData: LoginData;

  constructor(private loginService: AuthService) {
  }

  ngOnInit() {
    this.loginData = new LoginData();
  }

  login() {
    this.loginService.login(this.loginData);
  }

}
