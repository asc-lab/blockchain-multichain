export class Petition {
  userId: string;
  managerId: string;
  creationTime: string;
  requestContent: string;

  clear() {
    this.userId = '';
    this.managerId = '';
    this.creationTime = '';
    this.requestContent = '';
  }
}
