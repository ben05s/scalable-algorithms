import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { AbstractService } from '../../../../utils/AbstractService';

import { IdResponse } from '../../models/IdResponse';
import { TspProblem } from '../../models/TspProblem';
import { TspCity } from '../../models/TspCity';
import { TspAddProblemRequest } from '../requests/TspAddProblemRequest';

@Injectable()
export class TspProblemService extends AbstractService {
    getEndpoint() { return 'https://web-api-dot-clc3-project-benjamin.appspot.com/'; }

    getProblem(id: number): Observable<TspProblem> {
        return this.get<TspProblem>(`tsp/problem?id=${id}`);
    }

    getProblems(): Observable<TspProblem[]> {
      return this.get<TspProblem[]>('tsp/problem/list');
    }

    getCities(problemId: number): Observable<TspCity[]> {
        return this.get<TspCity[]>(`tsp/problem/cities?id=${problemId}`);
    }

    addProblem(request: TspAddProblemRequest): Observable<IdResponse> {
        return this.post<IdResponse>(`tsp/problem/add`, request);
    }
}
