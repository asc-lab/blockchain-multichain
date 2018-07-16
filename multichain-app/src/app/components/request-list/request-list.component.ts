import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Petition} from "../../shared/model/Petition";
import {RequestService} from "../../shared/services/request.service";
import {BaseRefreshComponent} from "../BaseRefreshComponent";
import {NotificationService} from "../../shared/notification.service";

@Component({
  selector: 'app-request-list',
  templateUrl: './request-list.component.html',
  styleUrls: ['./request-list.component.css']
})
export class RequestListComponent extends BaseRefreshComponent implements OnInit {

  requests: Petition[];

  constructor(protected cd: ChangeDetectorRef,
              private requestService: RequestService,
              private notifier: NotificationService) {
    super(cd);
  }

  ngOnInit() {
    this.requestService.getAll().subscribe(res => {
      console.log(res);
      this.requests = res;
      super.refreshView();
    });
  }

  accept(request: Petition) {
    this.requestService.accept(request).subscribe(res => {
      console.log(res);
      this.notifier.showSuccessMessage('Positive decision was created successfully!');
    }, err => {
      this.notifier.showErrorMessages(err.error.errors);
    });
  }

  decline(request: Petition) {
    this.requestService.decline(request).subscribe(res => {
      console.log(res);
      this.notifier.showSuccessMessage('Negative decision was created successfully!');
    }, err => {
      this.notifier.showErrorMessages(err.error.errors);
    });
  }
}
