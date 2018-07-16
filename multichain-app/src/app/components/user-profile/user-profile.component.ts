import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs/index';
import {Petition} from '../../shared/model/Petition';
import {RequestService} from '../../shared/services/request.service';
import {UserService} from '../../shared/user.service';
import {User} from '../../shared/model/User';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  requests$: Observable<Petition[]>;
  user: User;

  constructor(private requestService: RequestService,
              private userService: UserService) {
  }

  ngOnInit() {
    this.userService.getActualLoggedUser().subscribe(res => {
      this.user = res;
      this.requests$ = this.requestService.getForUser(this.user.username);
    });
  }

}
