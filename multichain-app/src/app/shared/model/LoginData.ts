export class LoginData {
  username?: string;
  password?: string;
  grant_type: string;

  constructor() {
    this.grant_type = "password";
  }
}
