import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'split'})
export class SplitPipe implements PipeTransform {
  transform(value: string, [separator]): string[] {
    if(!value)
      return [];

    return value.split(separator);
  }
}
