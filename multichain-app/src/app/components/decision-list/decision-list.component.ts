import {Component, OnInit} from '@angular/core';
import {Decision} from "../../shared/model/Decision";
import {DecisionService} from "../../shared/services/decision.service";
import {Observable} from "rxjs/index";

@Component({
  selector: 'app-decision-list',
  templateUrl: './decision-list.component.html',
  styleUrls: ['./decision-list.component.css']
})
export class DecisionListComponent implements OnInit {

  decisions$: Observable<Decision[]>;

  constructor(private decisionService: DecisionService) {
  }

  ngOnInit() {
    this.decisions$ = this.decisionService.getAll();
  }
}
