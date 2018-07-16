import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";
import {LoginData} from "../../model/LoginData";
import {NotificationService} from "../../notification.service";
import {CookieService} from "ngx-cookie-service";
import {ApiProvider} from "../api-provider.service";
import {AuthUser} from "../../model/AuthUser";

@Injectable()
export class AuthService {

  public static API_KEY = 'auth_token';

  constructor(private http: HttpClient,
              private router: Router,
              private cookie: CookieService,
              private notifier: NotificationService) {
  }

  login(login: LoginData) {
    console.log('Start login:');
    console.log(login);

    return this.http.post(ApiProvider.getAuthUrl(), this.createBody(login))
      .subscribe(
        res => this.processSuccessLogin(res),
        err => this.processErrorLogin(err)
      );
  }

  private createBody(login: LoginData) {
    let body = new HttpParams();
    body = body.set('username', login.username);
    body = body.set('password', login.password);
    body = body.set('grant_type', login.grant_type);
    return body;
  }

  private processSuccessLogin(res) {
    console.info(res);

    let user = <AuthUser> {
      access_token: res['access_token'],
      token_type: res['token_type'],
      refresh_token: res['refresh_token'],
      expires_in: res['expires_in'],
      scope: res['scope']
    };

    this.saveTokenInCookies(user.access_token);
    this.notifier.showSuccessMessage('Login successfully!');
    this.router.navigate(['/']);
  }

  private processErrorLogin(err) {
    console.log(err);

    if (err.status === 404)
      this.notifier.showErrorMessage('Server error.');
    else if (err.status === 400 || err.status === 401 || err.status === 403)
      this.notifier.showErrorMessage('Invalid login data.');
    else
      this.notifier.showErrorMessage('Unexpected error!');
  }

  logout() {
    this.clearCookies();
    this.notifier.showSuccessMessage('Logout successfully!');
    this.router.navigate(['login'])
  }

  isLogged(): boolean {
    return !!(this.cookie.get(AuthService.API_KEY));
  }

  private clearCookies() {
    this.cookie.deleteAll('../');
    this.cookie.deleteAll('/');
  }

  private saveTokenInCookies(token): void {
    if(this.isLogged())
      return;

    this.cookie.set(AuthService.API_KEY, token, undefined, "");
  }
}
