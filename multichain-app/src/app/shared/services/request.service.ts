import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/index";
import {Petition} from "../model/Petition";
import {ApiProvider} from "./api-provider.service";

import {DecisionService} from "./decision.service";

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(private http: HttpClient,
              private decisionService: DecisionService) {
  }

  getAll(): Observable<Petition[]> {
    return this.http.get<Petition[]>(ApiProvider.getUrl("/requests"));
  }

  getForUser(username: string): Observable<Petition[]> {
    return this.http.get<Petition[]>(ApiProvider.getUrl(/users/ + username + "/requests"));
  }

  save(petition: Petition) {
    console.log('Sent petition to API: ');
    console.log(petition);

    return this.http.post(ApiProvider.getUrl("/requests"), petition);
  }

  accept(request: Petition) {
    return this.decisionService.save(request, "ACCEPTED");
  }

  decline(request: Petition) {
    return this.decisionService.save(request, "DECLINED");
  }
}

