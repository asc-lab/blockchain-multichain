import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/index";
import {Block} from "./model/Block";
import {ApiProvider} from "./services/api-provider.service";
import {Chain} from "./model/Chain";

@Injectable({
  providedIn: 'root'
})
export class ChainExplorerService {

  constructor(private http: HttpClient) {
  }

  getAllBlocks(): Observable<Block[]> {
    return this.http.get<Block[]>(ApiProvider.getUrl("/blocks"));
  }

  getChainMainInfo(): Observable<Chain> {
    return this.http.get<Chain>(ApiProvider.getUrl("/chain"));
  }
}
