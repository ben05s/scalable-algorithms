import { Component, OnInit, Input, Inject, ViewChild } from '@angular/core';

import { DecimalPipe } from '@angular/common';

import { McTasksService } from '../../services/tasks/mc.tasks.service';
import { McTaskResult } from '../../models/McTaskResult';
import { TeamResult } from '../../models/TeamResult';
import { McTask } from '../../models/McTask';
import { MatDialogRef, MAT_DIALOG_DATA, MatTableDataSource, MatSort } from '@angular/material';
import { TimeMeasure } from '../../models/TimeMeasure';
import { AfterViewInit } from '@angular/core/src/metadata/lifecycle_hooks';

@Component({
  templateUrl: './mc.detail.component.html',
  styleUrls: ['./mc.detail.component.css']
})
export class McDetailComponent {

  displayedColumns = ['teamName', 'ratioPromotion', 'totalPromotions', 'ratioRelegation',
    'totalRelegations', 'highestSeasonScore', 'lowestSeasonScore', 'totalPoints'];
  dataSource = new MatTableDataSource<TeamResult>();

  @ViewChild(MatSort) sort: MatSort;

  task: McTask;
  runTime: number;
  computeTime: number;
  minComputeTime: number;
  avgComputeTime: number;
  maxComputeTime: number;
  currentIterations: number;
  iterationsPerWorker: string;
  taskResult: McTaskResult;

  constructor(
    public dialogRef: MatDialogRef<McDetailComponent>,
    @Inject(MAT_DIALOG_DATA) private _task: McTask,
    public _tasksService: McTasksService
  ) {
    this.task = _task;
  }

  ngOnInit() {
    const reducer = (accumulator, currentValue) => accumulator + currentValue;
    const average = arr => arr.reduce((p, c) => p + c, 0) / arr.length;

    this._tasksService.getTaskResult(this.task.id).subscribe(taskResult => {
      this.dataSource = new MatTableDataSource<TeamResult>(taskResult.teamResults);
      this.dataSource.sort = this.sort;

      this.iterationsPerWorker = ((this.task.iterations)/this.task.concurrentWorkers).toFixed(0);
      this.currentIterations = taskResult.calculatedIterations;
      if(this.task.started) {
        this.runTime = this.task.completed ? this.task.completed.valueOf() - this.task.started.valueOf() : new Date().valueOf() - this.task.started.valueOf();
      }
      if(taskResult.computeTimes) {
        this.computeTime = taskResult.computeTimes.reduce(reducer);
        this.minComputeTime = Math.min(...taskResult.computeTimes);
        this.maxComputeTime = Math.max(...taskResult.computeTimes);
        this.avgComputeTime = average(taskResult.computeTimes);
      }
      this.taskResult = taskResult;
    });
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  msToTime(duration: number) {
    if (!duration || duration == 0) return "";
    var milliseconds = ((duration % 1000) / 100)
      , seconds = ((duration / 1000) % 60)
      , minutes = ((duration / (1000 * 60)) % 60)
      , hours = ((duration / (1000 * 60 * 60)) % 24);
    var timeStr = "";
    if (hours >= 1) timeStr += hours.toFixed(0) + "h ";
    if (minutes >= 1) timeStr += minutes.toFixed(0) + "m ";
    if (seconds >= 1) timeStr += seconds.toFixed(0) + "s ";
    timeStr += milliseconds.toFixed(0) + "ms";
    return timeStr;
  }
}
