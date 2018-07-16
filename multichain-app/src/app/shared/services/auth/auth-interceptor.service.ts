import {Injectable} from '@angular/core';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {environment} from '../../../../environments/environment';
import {AuthService} from './auth.service';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private router: Router,
              private cookie: CookieService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('Add headers to request.');

    let headers = new HttpHeaders().set('Authorization', 'Basic ' + btoa(environment.client_id + ':' + environment.client_secret));

    if (this.cookie.get(AuthService.API_KEY)) {
      headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.cookie.get(AuthService.API_KEY));
    } else {
      headers.set('Content-Type', 'application/x-www-form-urlencoded');
    }

    req = req.clone({
      headers: headers
    });

    console.log('Request after add headers:');
    console.log(req);

    return next.handle(req).pipe(tap(() => {
    }, err => {
      console.log('Handle error in interceptor');
      console.log(err);
      if (err instanceof HttpErrorResponse && err.status === 401 || err.status === 403) {
        this.cookie.deleteAll('../');
        this.cookie.deleteAll('/');
        this.router.navigate(['/login']);
      }
    }));
  }
}
