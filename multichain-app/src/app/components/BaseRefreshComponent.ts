import {ChangeDetectorRef} from "@angular/core";

export class BaseRefreshComponent {

  constructor(protected changeDetectionRef: ChangeDetectorRef) {
  }

  protected refreshView() {
    if (!this.changeDetectionRef['destroyed']) {
      this.changeDetectionRef.detectChanges();
    }
  }
}
