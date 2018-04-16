import { Component, OnInit, OnDestroy, Input, Inject } from '@angular/core';
import { MatDialogRef, MatListOption, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';

import { TspOperators } from '../../../../models/TspOperators';

import { TspTaskService } from '../../../../services/task/tsp.task.service';
import { TspOperatorsService } from '../../../../services/operators/tsp.operators.service';
import { SelectionModel } from '@angular/cdk/collections';
import { TspAddTaskConfigRequest } from '../../../../services/requests/TspAddTaskConfigRequest';

@Component({
  templateUrl: './tsp.tasks.details.config.component.html',
  styleUrls: ['./tsp.tasks.details.config.component.css']
})
export class TspTasksDetailsConfigComponent implements OnInit {

    taskId = 0;

    selections: number[] = [];
    crossovers: number[] = [];
    mutations: number[] = [];
    elitisms: number[] = [];
    mutationRates: number[] = [];
    iterations: number[] = [];
    populationSizes: number[] = [];

    mutationRate = 0.05;
    populationSize = 100;
    iteration = 200;
    runs = 1;

    operators: TspOperators = new TspOperators();

    requests: TspAddTaskConfigRequest[] = [];
    ids: number[] = [];
    sendingRequests = false;

    constructor(
        private _router: Router,
        private _dialogRef: MatDialogRef<TspTasksDetailsConfigComponent>,
        private _operatorService: TspOperatorsService,
        private _taskService: TspTaskService,
        @Inject(MAT_DIALOG_DATA) private data: any) {
        this.taskId = data.taskId;
    }

    ngOnInit(): void {
        this.loadOperators();
    }

    closeDialog(ids: number[]): void {
        this._dialogRef.close(ids);
    }

    addMutationRate(): void {
        this.mutationRates.push(this.mutationRate);
    }

    removeMutationRate(val: number): void {
        const index = this.mutationRates.indexOf(val);
        if (index >= 0) { this.mutationRates.splice(index, 1); }
    }

    addPopulationSize(): void {
        this.populationSizes.push(this.populationSize);
    }

    removePopulationSize(val: number): void {
        const index = this.populationSizes.indexOf(val);
        if (index >= 0) { this.populationSizes.splice(index, 1); }
    }

    addIteration(): void {
        this.iterations.push(this.iteration);
    }

    removeIteration(val: number): void {
        const index = this.iterations.indexOf(val);
        if (index >= 0) { this.iterations.splice(index, 1); }
    }

    loadOperators(): void {
        this._operatorService.getOperators().subscribe(
            operators => {
                this.operators = operators;
            }
        );
    }

    sendAddConfigRequests(): void {
        this.requests = [];
        for (const curSelection of this.selections) {
        for (const curCrossover of this.crossovers) {
        for (const curMutation of this.mutations) {
        for (const curElitism of this.elitisms) {
        for (const curMutationRate of this.mutationRates) {
        for (const curIteration of this.iterations) {
        for (const curPopulationSize of this.populationSizes) {
            this.requests.push(this.buildAddRequest(
                curSelection,
                curCrossover,
                curMutation,
                curMutationRate,
                // tslint:disable-next-line:triple-equals
                curElitism == 1,
                curPopulationSize,
                curIteration
            ));
        }}}}}}}
        this.sendRequests();
    }

    private sendRequests() {
        const request = this.getRequest();
        if (request !== null) {
            this.sendingRequests = true;
            this._taskService.addTaskConfig(request).subscribe(response => {
                this.ids.push(response.id);
                this.sendRequests();
            });
        } else {
            this.sendingRequests = false;
            this.closeDialog(this.ids);
        }
    }

    private getRequest(): TspAddTaskConfigRequest {
        if (this.requests.length <= 0) { return null; }
        return this.requests.pop();
    }

    private buildAddRequest(
        selection: number,
        crossover: number,
        mutation: number,
        mutationRate: number,
        elitism: boolean,
        populationSize: number,
        iterations: number
    ): TspAddTaskConfigRequest {
        const request = new TspAddTaskConfigRequest();
        request.taskId = this.taskId;
        request.selection = selection;
        request.crossover = crossover;
        request.mutation = mutation;
        request.mutationRate = mutationRate;
        request.elitism = elitism;
        request.populationSize = populationSize;
        request.iterations = iterations;
        request.runs = this.runs;
        return request;
    }
}
