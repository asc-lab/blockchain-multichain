import { Pipe, PipeTransform } from '@angular/core';
import {Petition} from '../model/Petition';

@Pipe({name: 'petitionFullInfo'})
export class PetitionFullInfoPipe implements PipeTransform {

  transform(value: Petition): string {
    return `User: ${value.userId}; Manager: ${value.managerId}; CreationTime: ${value.creationTime}; Content: ${value.requestContent}`;
  }

}
