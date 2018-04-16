import { Component, OnInit, OnDestroy, Input, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';

import { TspOperators } from '../../../../models/TspOperators';
import { TspTaskConfigRun } from '../../../../models/TspTaskConfigRun';

import { TspTaskService } from '../../../../services/task/tsp.task.service';
import { TspOperatorsService } from '../../../../services/operators/tsp.operators.service';
import { TspTaskConfigRunIteration } from '../../../../models/TspTaskConfigRunIteration';
import { TspProblemService } from '../../../../services/problem/tsp.problem.service';
import { TspCity } from '../../../../models/TspCity';

@Component({
  templateUrl: './tsp.tasks.details.run.component.html',
  styleUrls: ['./tsp.tasks.details.run.component.css']
})
export class TspTasksDetailsRunComponent implements OnInit {

    runId = 0;
    cities: TspCity[] = [];
    _iteration = 1;
    maxIteration = 0;

    runIteration: TspTaskConfigRunIteration = new TspTaskConfigRunIteration();

    get iteration() {
        return this._iteration;
    }

    set iteration(iteration: number) {
        this._iteration = iteration;
        this.loadIteration(iteration);
    }

    constructor(
        private _router: Router,
        private _dialogRef: MatDialogRef<TspTasksDetailsRunComponent>,
        private _taskService: TspTaskService,
        private _problemService: TspProblemService,
        @Inject(MAT_DIALOG_DATA) private data: any) {
        this.runId = data.runId;
        this.cities = data.cities;
        this.maxIteration = data.maxIteration;
        this.loadIteration(1);
    }

    ngOnInit(): void {}

    loadIteration(iteration: number): void {
        if (this.runId <= 0) { return; }
        this._taskService.getTaskConfigRunIteration(this.runId, iteration).subscribe(
            runIteration => {
                this.runIteration = runIteration;
            }
        );
    }
}
