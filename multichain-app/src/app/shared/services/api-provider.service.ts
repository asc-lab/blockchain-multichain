import {environment} from '../../../environments/environment';

export class ApiProvider {

  static getUrl(next: string) {
    if (!next.startsWith('/')) {
      next = '/' + next;
    }
    return environment.app_url + '/api' + next;
  }

  static getAuthUrl() {
    return environment.app_url + '/oauth/token';
  }

}
