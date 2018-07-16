import {Component, OnInit} from '@angular/core';
import {Petition} from '../../shared/model/Petition';
import {RequestService} from '../../shared/services/request.service';
import {NotificationService} from '../../shared/notification.service';
import {UserService} from '../../shared/user.service';
import {User} from '../../shared/model/User';
import * as moment from 'moment';

@Component({
  selector: 'app-request-new',
  templateUrl: './request-new.component.html',
  styleUrls: ['./request-new.component.css']
})
export class RequestNewComponent implements OnInit {

  petition: Petition = new Petition();
  managers: User[] = [];

  constructor(private requestService: RequestService,
              private userService: UserService,
              private notifier: NotificationService) {
  }

  ngOnInit(): void {
    this.userService.getManagers().subscribe(res => {
      this.managers = res;
    });
  }

  save() {
    this.petition.creationTime = moment().toISOString();
    this.requestService.save(this.petition).subscribe(res => {
      console.log(res);
      this.notifier.showSuccessMessage('Petition save successfully!');
      this.petition.clear();
    });
  }

}
