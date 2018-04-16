import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { AbstractService } from '../../../../utils/AbstractService';

import { IdResponse } from '../../models/IdResponse';
import { IdDescriptionEntry } from '../../models/TspOperators';
import { TspTask } from '../../models/TspTask';
import { TspAddTaskRequest } from '../requests/TspAddTaskRequest';
import { TspAddTaskConfigRequest } from '../requests/TspAddTaskConfigRequest';
import { TspTaskConfig } from '../../models/TspTaskConfig';
import { TspTaskConfigRun } from '../../models/TspTaskConfigRun';
import { TspTaskConfigRunIteration } from '../../models/TspTaskConfigRunIteration';
import { TspTaskProgress } from '../../models/TspTaskProgress';

@Injectable()
export class TspTaskService extends AbstractService {
    getEndpoint() { return 'https://web-api-dot-clc3-project-benjamin.appspot.com/'; }

    // task
    getTask(id: number): Observable<TspTask> {
        return this.get<TspTask>(`tsp/task?id=${id}`);
    }

    getTasks(): Observable<TspTask[]> {
        return this.get<TspTask[]>('tsp/task/list');
    }

    addTask(request: TspAddTaskRequest): Observable<IdResponse> {
        return this.post<IdResponse>('tsp/task/add', request);
    }

    deleteTask(taskId: number) {
        return this.get<any>(`tsp/task/delete?id=${taskId}`);
    }

    startTask(id: number): Observable<any> {
        return this.get<any>(`tsp/task/start?id=${id}`);
    }

    // task config
    addTaskConfig(request: TspAddTaskConfigRequest): Observable<IdResponse> {
        return this.post<IdResponse>('tsp/task/config/add', request);
    }

    getTaskConfigs(taskId: number): Observable<TspTaskConfig[]> {
        return this.get<TspTaskConfig[]>(`tsp/task/config/list?id=${taskId}`);
    }

    // task config run)
    getTaskConfigRunsByTaskId(taskId: number): Observable<TspTaskConfigRun[]> {
        return this.get<TspTaskConfigRun[]>(`tsp/task/config/runs/task?id=${taskId}`);
    }
    getTaskConfigRuns(taskConfigId: number): Observable<TspTaskConfigRun[]> {
        return this.get<TspTaskConfigRun[]>(`tsp/task/config/runs?id=${taskConfigId}`);
    }

    getTaskConfigRun(taskConfigRunId: number): Observable<TspTaskConfigRun> {
        return this.get<TspTaskConfigRun>(`tsp/task/config/run?id=${taskConfigRunId}`);
    }

    // task config run iterations
    getTaskConfigRunIterations(taskConfigRunId: number): Observable<TspTaskConfigRunIteration[]> {
        return this.get<TspTaskConfigRunIteration[]>(`tsp/task/config/run/iterations?id=${taskConfigRunId}`);
    }

    getTaskConfigRunIteration(taskConfigRunId: number, iteration: number): Observable<TspTaskConfigRunIteration> {
        return this.get<TspTaskConfigRunIteration>(`tsp/task/config/run/iteration?id=${taskConfigRunId}&iteration=${iteration}`);
    }

    // task progress
    getTaskProgress(taskId: number): Observable<TspTaskProgress> {
        return this.get<TspTaskProgress>(`tsp/task/progress?id=${taskId}`);
    }
}
