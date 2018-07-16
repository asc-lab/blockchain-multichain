import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/index';
import {Decision, DecisionResult} from '../model/Decision';
import {ApiProvider} from './api-provider.service';
import {Petition} from '../model/Petition';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class DecisionService {

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Decision[]> {
    return this.http.get<Decision[]>(ApiProvider.getUrl('/decisions'));
  }

  save(request: Petition, result: DecisionResult) {
    const decision: Decision = new Decision();
    decision.decisionTime = moment().toISOString();
    decision.request = request;
    decision.decisionResult = result;

    return this.http.post(ApiProvider.getUrl('/decisions'), decision);
  }
}

