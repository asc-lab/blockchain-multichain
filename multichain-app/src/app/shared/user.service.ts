import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/index';
import {User} from './model/User';
import {ApiProvider} from './services/api-provider.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getActualLoggedUser(): Observable<User> {
    return this.http.get<User>(ApiProvider.getUrl('/users/me'));
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(ApiProvider.getUrl('/users'));
  }

  getManagers(): Observable<User[]> {
    return this.http.get<User[]>(ApiProvider.getUrl('/managers'));
  }
}
