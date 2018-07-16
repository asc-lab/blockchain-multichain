import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from './shared/services/auth/auth.service';
import {environment} from '../environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppComponent implements OnInit {

  actualVersion = '';

  constructor(private authService: AuthService,
              private router: Router) {

    if (!this.isLogged()) {
      this.router.navigate(['login']);
    }
  }

  ngOnInit() {
    this.actualVersion = environment.version;
  }

  isLogged(): boolean {
    return this.authService.isLogged();
  }

}
