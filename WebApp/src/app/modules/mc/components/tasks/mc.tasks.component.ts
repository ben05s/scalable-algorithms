import { Component, OnInit, OnDestroy, ViewChild, ChangeDetectorRef } from '@angular/core';

import {MatPaginator, MatSort, MatTableDataSource, MatDialog} from '@angular/material';

import { McTask } from '../../models/McTask';
import { McTasksService } from '../../services/tasks/mc.tasks.service';
import { Subscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/observable/timer';
import { filter } from 'rxjs/operators';
import { McSettingsComponent } from '../settings/mc.settings.component';
import { TaskStatus } from '../../models/TaskStatus';
import { McDetailComponent } from '../detail/mc.detail.component';
import { TasksDataSource } from './TasksDataSource';
import { McTaskProgress } from '../../models/McTaskProgress';

@Component({
  selector: 'mc-tasks',
  templateUrl: './mc.tasks.component.html',
  styleUrls: ['./mc.tasks.component.css']
})
export class McTasksComponent implements OnInit, OnDestroy {

    progressTimerInterval: number = 10000;
    progressTimerSubscriptions: Map<number, Subscription> = new Map<number, Subscription>();

    displayedColumns = ['name', 'iterations', 'concurrentWorkers', 'created', 'started', 'completed', 'duration', 'progress', 'state', 'controls'];
    dataSource: TasksDataSource;

    tasks: McTask[];
    selectedTask: McTask;

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(
        private changeDetectorRefs: ChangeDetectorRef,
        private _tasksService: McTasksService,
        public dialogAddTask: MatDialog, 
        public dialogDetailTask: MatDialog
    ) {}

    ngOnInit() {
        this.loadTasks();
    }

    ngOnDestroy() {
        if (this.progressTimerSubscriptions.size > 0) {
            this.progressTimerSubscriptions.forEach((s) => s.unsubscribe());
        }
    }

    private loadTasks(): void {
        this._tasksService.getTasks().subscribe(tasks => {
            this.tasks = tasks;

            // update progress
            tasks.filter(t => t.status == TaskStatus.STOPPED || t.status == TaskStatus.ERROR)
                .map(t => {
                    this._tasksService.getProgress(t.id).subscribe(progress => {
                        t = this.updateProgressOfTask(progress[t.id], t);
                })
            });
            tasks.filter(t => t.status == TaskStatus.DONE).map(t => t.progress = 100.0);
            tasks.filter(t => t.status == TaskStatus.QUEUED || t.status == TaskStatus.STARTED)
                .map(t => this.updateProgress(t.id) /* this.startProgressTimer(t.id) */);
            
            // init dataSource from table
            this.dataSource = new TasksDataSource(tasks, this.paginator, this.sort);
        });
    }

    private updateProgress(taskId: number): void {
        this.tasks.filter(t => t.id == taskId && t.progress == 100.0)
            .map(t => {
                this.progressTimerSubscriptions.get(taskId).unsubscribe();
                this._tasksService.getTask(t.id).subscribe(task => {
                    task.progress = 100.0;
                    this.updateTableRow(task);
                });
            });

        this.tasks.filter(t => t.id == taskId && (t.status == TaskStatus.QUEUED || t.status == TaskStatus.STARTED))
            .map(t => {
                this._tasksService.getProgress(taskId).subscribe(progress => {
                    t = this.updateProgressOfTask(progress[t.id], t);
                });
            });
        
        this.dataSource = new TasksDataSource(this.tasks, this.paginator, this.sort);
        this.changeDetectorRefs.detectChanges();
    }

    private startProgressTimer(taskId: number): void {
        var timer = Observable.timer(0,this.progressTimerInterval).subscribe(() => this.updateProgress(taskId));
        this.progressTimerSubscriptions.set(taskId, timer);
    }

    varToEnumStringVal(value: number): string {
        var en: { [index: string]: any } = TaskStatus;
        return en[value];
    }

    selectTask(task: McTask) {
        this.selectedTask = task;
    }

    openAddTaskDialog(): void {
        let dialogRef = this.dialogAddTask.open(McSettingsComponent, {
            width: '500px'
        });

        dialogRef.afterClosed()
            .pipe(filter(task => task && task.id))
            .subscribe(task => {
                this.tasks.unshift(task);
                this.dataSource = new TasksDataSource(this.tasks, this.paginator, this.sort);
                this.changeDetectorRefs.detectChanges();
        });
    }

    openDetailTaskDialog(task: McTask): void {
        let dialogRef = this.dialogDetailTask.open(McDetailComponent, {
            width: '60%',
            data: task
        });

        dialogRef.afterClosed().subscribe(result => {
        });
    }

    runTask(task: McTask) {
        this._tasksService.enqueueTask(task.id).subscribe(task => {
            this.updateTableRow(task);
            this.updateProgress(task.id);
            /* this.startProgressTimer(task.id); */
        });
    }

    stopTask(task: McTask) {
        this._tasksService.dequeueTask(task.id).subscribe(task => {
            this.updateTableRow(task);
        });
    }

    runAllTasks() {
        let runnableTasks = this.tasks
            .filter(t => t.status == TaskStatus.CREATED)
            .map(t => this.runTask(t));
    }

    refresh() {
        this.loadTasks();
    }

    updateTableRow(task: McTask): void {
        var index = this.tasks.findIndex(t => t.id == task.id);
        this.tasks[index] = task;
        
        this.dataSource = new TasksDataSource(this.tasks, this.paginator, this.sort);
        this.changeDetectorRefs.detectChanges();
    }

    updateProgressOfTask(p: McTaskProgress, t: McTask): McTask {
        t.progress = p.progress;
        t.duration = p.duration;
        t.status = p.taskStatus;
        if(!t.started) t.started = new Date();
        return t;
    }

    msToTime(duration: number) {
        if(!duration || duration == 0) return "";
        var milliseconds = ((duration%1000)/100)
            , seconds = ((duration/1000)%60)
            , minutes = ((duration/(1000*60))%60)
            , hours = ((duration/(1000*60*60))%24);
        var timeStr = "";
        if(hours >= 1) timeStr += hours.toFixed(0) + "h ";
        if(minutes >= 1) timeStr += minutes.toFixed(0)+ "m ";
        timeStr += seconds.toFixed(0) + "s ";
        return timeStr;
      }

      numberWithCommas = (x) => {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }
}
