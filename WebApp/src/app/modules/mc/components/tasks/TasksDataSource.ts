import { MatPaginator, MatSort } from '@angular/material';
import { DataSource } from '@angular/cdk/collections';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/of';

export class TasksDataSource extends DataSource<any> {

  constructor(private tasks, private paginator: MatPaginator, private sort: MatSort) {
    super();
  }

  connect(): Observable<any> {
    return Observable.of(this.tasks);
  }

  disconnect() {
  }
}