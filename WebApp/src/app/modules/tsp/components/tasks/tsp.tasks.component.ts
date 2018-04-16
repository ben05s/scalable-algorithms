import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material';

import { Subscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';

import { TspTaskService } from '../../services/task/tsp.task.service';
import { TspTask } from '../../models/TspTask';
import { TspTasksCreateComponent } from './create/tsp.tasks.create.component';

@Component({
  selector: 'tsp-tasks',
  templateUrl: './tsp.tasks.component.html',
  styleUrls: ['./tsp.tasks.component.css']
})
export class TspTasksComponent implements OnInit, OnDestroy {
    // taskProgress: TspTaskProgressResponse = new TspTaskProgressResponse();
    // progressTimerSubscription: Subscription;

    isLoading = false;

    tasks: TspTask[] = new Array<TspTask>();
    tasksSubscription: Subscription;

    constructor(private _dialog: MatDialog,
                private _taskService: TspTaskService) {}

    ngOnInit(): void {
        this.loadTasks();
    }

    ngOnDestroy(): void {
        this.unsubscribeTasks();
    }

    openAddTaskDialog(): void {
        const dialogRef = this._dialog.open(TspTasksCreateComponent, {
            width: '450px'
        });

        dialogRef.afterClosed().subscribe(id => {
            // if (id) {
            //     this.loadProblem(id);
            // }
        });
    }

    loadTasks() {
        this.isLoading = true;
        this.unsubscribeTasks();
        this.tasksSubscription = this._taskService.getTasks().subscribe(
            tasks => {
                this.isLoading = false;
                this.tasks = tasks;
            },
            error => {
                this.isLoading = false;
                this.tasks = new Array<TspTask>();
            }
        );
    }

    unsubscribeTasks(): void {
        if (this.tasksSubscription) {
            this.tasksSubscription.unsubscribe();
        }
    }
}
