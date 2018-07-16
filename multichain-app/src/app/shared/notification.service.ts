import {Injectable} from '@angular/core';
import {NotificationsService} from "angular2-notifications";

type Types = 'success' | 'info' | 'warning' | 'error';

@Injectable()
export class NotificationService {

  private readonly notifier: NotificationsService;

  constructor(private _service: NotificationsService) {
    this.notifier = _service;
  }

  showSuccessMessage(msg: string): void {
    this.notifier.success('Success!', msg);
  }

  showInfoMessage(msg: string): void {
    this.notifier.info('Info!', msg);
  }

  showWarnMessage(msg: string): void {
    this.notifier.warn('Warning!', msg);
  }

  showErrorMessage(msg: string): void {
    this.notifier.error('Error!', msg);
  }

  showErrorMessages(errors: string[]) {
    errors.forEach(e => this.showErrorMessage(e));
  }
}
