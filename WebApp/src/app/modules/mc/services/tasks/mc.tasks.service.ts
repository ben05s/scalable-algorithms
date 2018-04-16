import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { McTask } from '../../models/McTask';
import { McTaskSettings } from '../../models/McTaskSettings';
import { AbstractService } from '../../../../utils/AbstractService';
import { League } from '../../models/League';
import { McTaskResult } from '../../models/McTaskResult';
import { McTaskProgress } from '../../models/McTaskProgress';

@Injectable()
export class McTasksService extends AbstractService {

    getEndpoint() { return 'https://web-api-dot-clc3-project-benjamin.appspot.com/'; }

    getTasks() {
      return this.getData<McTask[]>('mc/task');
    }

    getTask(taskId) {
      return this.getData<McTask>('mc/task?id=' + taskId);
    }

    addTask(taskSettings: McTaskSettings) {
      return this.postData<McTask>('mc/task/add', taskSettings);
    }

    enqueueTask(taskId: number) {
      return this.getData<McTask>('mc/task/enqueue?id=' + taskId);
    }

    dequeueTask(taskId: number) {
      return this.getData<McTask>('mc/task/dequeue?id=' + taskId);
    }

    getTaskResult(taskId: number) {
      return this.getData<McTaskResult>('mc/task/result?id=' + taskId);
    }

    getProgress(taskId: number) {
      return this.getData<Map<number, McTaskProgress>>('mc/task/progress?id=' + taskId);
    }

    uploadPgnFile(fileName: string, fileContent: string) {
      return this.postData<League>('mc/upload/pgn', { fileName: fileName, fileContent: fileContent });
    }

} 
