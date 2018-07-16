import {Petition} from "./Petition";

export declare type DecisionResult = 'ACCEPTED' | 'DECLINED';

export class Decision {
  decisionResult: DecisionResult;
  decisionTime: string;
  request: Petition;
  managerId: string;
}
