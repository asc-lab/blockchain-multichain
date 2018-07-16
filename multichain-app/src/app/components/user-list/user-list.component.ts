import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs/index';
import {User} from '../../shared/model/User';
import {UserService} from '../../shared/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users$: Observable<User[]>;
  managers$: Observable<User[]>;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.users$ = this.userService.getUsers();
    this.managers$ = this.userService.getManagers();
  }

}
