import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'sortBy'})
export class SortByPipe implements PipeTransform {
    transform(array: Array<any>, args?: any): Array<any> {
        return array.sort((a: any, b: any) => {
            if (a[args.property] < b[args.property]) {
              return -1 * args.order;
            } else if (a[args.property] > b[args.property]) {
              return 1 * args.order;
            } else {
              return 0;
            }
        });
    }
}
