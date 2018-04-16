import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { TspTask } from '../../models/TspTask';
import { AbstractService } from '../../../../utils/AbstractService';
import { TspOperators } from '../../models/TspOperators';

@Injectable()
export class TspOperatorsService extends AbstractService {

    getEndpoint() { return 'https://web-api-dot-clc3-project-benjamin.appspot.com/'; }

    getOperators(): Observable<TspOperators> {
      return this.get<TspOperators>('tsp/operators');
    }
}
