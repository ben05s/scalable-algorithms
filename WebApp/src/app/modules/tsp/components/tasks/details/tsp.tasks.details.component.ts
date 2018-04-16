import { Component, OnInit, OnDestroy, ViewChild, Input, AfterViewInit } from '@angular/core';
import { MatDialog, MatTableDataSource, MatSort } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
// models
import { TspOperators } from '../../../models/TspOperators';
import { TspTask } from '../../../models/TspTask';
import { TspTaskConfig } from '../../../models/TspTaskConfig';
import { TspTaskConfigRun } from '../../../models/TspTaskConfigRun';
// services
import { TspTaskService } from '../../../services/task/tsp.task.service';
import { TspTasksDetailsConfigComponent } from './config/tsp.tasks.details.config.component';
import { TspTasksDetailsRunComponent } from './run/tsp.tasks.details.run.component';
import { TspOperatorsService } from '../../../services/operators/tsp.operators.service';
import { TspTaskProgress } from '../../../models/TspTaskProgress';
import { Subscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';

class RunDetails {
    id: number;
    status: string;
    started: string;
    completed: string;
    fitness: number;
    progress: number;
    color: string;
}

class ConfigDetails {
    id: number;
    selection: number;
    crossover: number;
    mutation: number;
    mutationRate: number;
    elitism: boolean;
    populationSize: number;
    iterations: number;
    fitness: number;
    progress: number;
    runs: MatTableDataSource<RunDetails> = new MatTableDataSource<RunDetails>();
}

@Component({
  selector: 'tsp-tasks-details',
  templateUrl: './tsp.tasks.details.component.html',
  styleUrls: ['./tsp.tasks.details.component.css']
})
export class TspTasksDetailsComponent implements OnInit, OnDestroy {

    progressIntervalMs = 2000;
    startDisabled = false;

    _taskId = 0;
    task: TspTask = new TspTask();
    configs: ConfigDetails[] = new Array<ConfigDetails>();
    // taskConfigs: TspTaskConfig[] = new Array<TspTaskConfig>();
    // taskConfigRuns = new Map<number, TspTaskConfigRun[]>();

    selectionMap = new Map<number, string>();
    crossoverMap = new Map<number, string>();
    mutationMap = new Map<number, string>();
    computeTime = '';

    taskProgress: TspTaskProgress = new TspTaskProgress();
    progressTimerSubscription: Subscription;

    displayedColumns = ['Id', 'Started', 'Fitness', 'Status', 'Completed', 'Progress'];

    get taskId(): number {
        return this._taskId;
    }

    @Input()
    set taskId(taskId: number) {
        this._taskId = taskId;
        this.loadOperators();
        this.loadTaskDetails();
        this.loadTaskConfigs();
        this.startProgressTimer(0);
    }

    constructor(private _dialog: MatDialog,
                private _taskService: TspTaskService,
                private _operatorsService: TspOperatorsService,
                private _route: ActivatedRoute,
                private _router: Router) {
        this._route.params.subscribe(params => {
            this.taskId = params.taskId;
        });
    }

    ngOnDestroy(): void {
        this.unsubscribeProgressTimer();
    }

    ngOnInit(): void {}

    start(): void {
        this.startDisabled = true;
        this._taskService.startTask(this._taskId).subscribe(resp => {});
        this.startProgressTimer(0);
    }

    delete(): void {
        this._taskService.deleteTask(this._taskId).subscribe(resp => {
            this._router.navigate(['tsp/tasks']);
        });
    }

    private loadOperators(): void {
        this._operatorsService.getOperators().subscribe(operators => {
            for (const selection of operators.selection) {
                this.selectionMap.set(selection.id, selection.description);
            }
            for (const crossover of operators.crossover) {
                this.crossoverMap.set(crossover.id, crossover.description);
            }
            for (const mutation of operators.mutation) {
                this.mutationMap.set(mutation.id, mutation.description);
            }
        });
    }

    private loadTaskDetails(): void {
        if (this._taskId <= 0) { return; }
        this._taskService.getTask(this._taskId).subscribe(task => {
            this.task = task;
            this.taskProgress.bestTour = task.bestIteration.tour;
            this.taskProgress.taskDetails.bestFitness = task.bestIteration.fitness;
        });
    }

    private loadTaskConfigs(): void {
        if (this._taskId <= 0) { return; }
        this._taskService.getTaskConfigs(this._taskId).subscribe(taskConfigs => {
            const configs = new Array<ConfigDetails>();
            for (const config of taskConfigs) {
                const details = new ConfigDetails();
                details.id = config.taskConfigId;
                details.selection = config.selection;
                details.crossover = config.crossover;
                details.mutation = config.mutation;
                details.mutationRate = config.mutationRate;
                details.elitism = config.elitism;
                details.populationSize = config.populationSize;
                details.progress = 0;
                details.iterations = config.iterations;
                details.fitness = Number.MAX_VALUE;
                details.runs = new MatTableDataSource<RunDetails>();
                configs.push(details);
            }
            this.configs = configs;
            // for (let i = 0; i < configs.length; ++i) {
            //     this.loadTaskConfigRuns(i, this.configs[i].id);
            // }
            this.loadTaskConfigRuns();
        });
    }

    private getIndexByRunId(runId: number) {
        for (let i = 0; i < this.configs.length; ++i) {
            const config = this.configs[i];
            for (const run of config.runs.data) {
                if (runId === run.id) {
                    return config.id;
                }
            }
        }
        return 0;
    }

    private loadTaskConfigRuns(): void {
        this._taskService.getTaskConfigRunsByTaskId(this.taskId).subscribe(runs => {
            for (const run of runs) {
                for (const config of this.configs) {
                    if (config.id === run.configId) {
                        const details = new RunDetails();
                        details.id = run.id;
                        details.status = this.getStatus(run.status);
                        details.started = this.valueOr(run.started, 'Not started');
                        details.completed = this.valueOr(run.completed, 'Not completed');
                        details.fitness = run.latestIteration.fitness;
                        details.progress = (run.latestIteration.iteration / run.maxIteration) * 100;
                        details.color = 'white';
                        config.runs.data.push(details);
                        break;
                    }
                }
            }
            for (const config of this.configs) {
                config.runs.data = config.runs.data.sort(this.sortRunByFitness);
            }
            this.processNewProgress(this.taskProgress);
        });
    }

    // private loadTaskConfigRuns(/*index: number, taskConfigId: number*/): void {
    //     this._taskService.getTaskConfigRunsByTaskId(this.taskId).subscribe(configRuns => {
    //     // this._taskService.getTaskConfigRuns(taskConfigId).subscribe(configRuns => {
    //         let runs = new Array<RunDetails>();
    //         let minFitness = Number.MAX_VALUE;
    //         let maxFitness = Number.MIN_VALUE;
    //         for (const run of configRuns) {
    //             const fitness = run.latestIteration.fitness;
    //             if (fitness > maxFitness) { maxFitness = fitness; }
    //             if (fitness < minFitness) { minFitness = fitness; }
    //         }

    //         for (const run of configRuns) {
    //             const details = new RunDetails();
    //             details.id = run.id;
    //             details.status = this.getStatus(run.status);
    //             details.started = run.started;
    //             details.completed = run.completed;
    //             details.fitness = run.latestIteration.fitness;
    //             details.progress = (run.latestIteration.iteration / run.maxIteration) * 100;
    //             details.color = this.getRunColor(run.status, details.fitness, minFitness, maxFitness);
    //             runs.push(details);
    //         }
    //         runs = runs.sort((a: any, b: any) => {
    //             if (a.fitness < b.fitness) {
    //                 return -1;
    //             } else if (a.fitness > b.fitness) {
    //                 return 1;
    //             } else {
    //                 return 0;
    //             }
    //         });
    //         const configIndex = getIndexByRunId()
    //         this.configs[index].runs = new MatTableDataSource(runs);
    //         this.processNewProgress(this.taskProgress);
    //     });
    // }

    dhm(t) {
        const cd = 24 * 60 * 60 * 1000,
            ch = 60 * 60 * 1000,
            pad = function(n){ return n < 10 ? '0' + n : n; };
        let d = Math.floor(t / cd),
            h = Math.floor( (t - d * cd) / ch),
            m = Math.round( (t - d * cd - h * ch) / 60000);
      if (m === 60) {
        h++;
        m = 0;
      }
      if (h === 24) {
        d++;
        h = 0;
      }
      return [d, pad(h), pad(m)].join(':');
    }

    msToTime(duration: number) {
        // tslint:disable-next-line:triple-equals
        if (!duration || duration == 0) { return ''; }
        const milliseconds = ((duration % 1000) / 100)
          , seconds = ((duration / 1000) % 60)
          , minutes = ((duration / (1000 * 60)) % 60)
          , hours = ((duration / (1000 * 60 * 60)) % 24);
        let timeStr = '';
        if (hours >= 1) { timeStr += hours.toFixed(0) + 'h '; }
        if (minutes >= 1) { timeStr += minutes.toFixed(0) + 'm '; }
        if (seconds >= 1) { timeStr += seconds.toFixed(0) + 's '; }
        return timeStr;
      }


    private getComputeTime() {
        let computeTime = 0;
        for (const config of this.configs) {
            for (const run of config.runs.data) {
                const startedDate = new Date(run.started);
                let completedDate = new Date(run.completed);
                if (run.completed === 'Not completed') {
                    const curDate = new Date();
                    curDate.setTime(curDate.getTime() - (60 * 60 * 1000));
                    completedDate = curDate;
                }
                if (run.started !== 'Not started') {
                    computeTime += completedDate.getTime() - startedDate.getTime();
                }
            }
        }
        return computeTime;
    }

    private getStatus(status: number) {
        switch (status) {
            case 1: return 'Idle';
            case 2: return 'Queued';
            case 3: return 'Started';
            case 4: return 'Done';
        }
        return 'Created';
    }

    private getRunColor(status: number, fitness: number, minFitness: number, maxFitness: number) {
        if (status !== 1 && status !== 0 && status !== 2) {
            if (fitness === minFitness) {
                return 'green';
            } else if (fitness === maxFitness) {
                return 'red';
            }
        }
        return 'white';
    }

    openAddConfigDialog(): void {
        const configDialogRef = this._dialog.open(TspTasksDetailsConfigComponent, {
            width: '850px',
            data: {
                taskId: this._taskId
            }
        });

        configDialogRef.afterClosed().subscribe(ids => {
            this.loadTaskConfigs();
        });
    }

    getMaxIterations(taskConfigId: number): number {
        for (const config of this.configs) {
            if (config.id === taskConfigId) {
                return config.iterations;
            }
        }
        return 0;
    }

    openRunDialog(taskConfigId: number, taskConfigRunId: number): void {
        this._dialog.open(TspTasksDetailsRunComponent, {
            width: '400px',
            data: {
                runId: taskConfigRunId,
                cities: this.task.problem.cities,
                maxIteration: this.getMaxIterations(taskConfigId)
            }
        });
    }

    loadProgress() {
        this._taskService.getTaskProgress(this.taskId).subscribe(
            progress => {
                this.taskProgress = progress;
                this.processNewProgress(progress);
                if (this.taskProgress.taskDetails.progress >= 1) {
                    // stop timer, we are done
                    this.unsubscribeProgressTimer();
                } else {
                    if (this.startDisabled || this.taskProgress.taskDetails.progress > 0) {
                        this.startProgressTimer(this.progressIntervalMs); // restart timer
                    }
                }
            },
            error => {
                this.startProgressTimer(this.progressIntervalMs); // restart timer
            }
        );
    }

    // quick fix / workaround angular crap
    getConfigFitness(id: number) {
        if (this.taskProgress.taskConfigProgress[id] !== undefined) {
            if (this.taskProgress.taskConfigProgress[id].bestFitness > 10000) {
                return 0; // another workaround
            }
            return this.taskProgress.taskConfigProgress[id].bestFitness;
        }
        return 0; // test
    }

    precisionRound(number, precision) {
        const factor = Math.pow(10, precision);
        return Math.round(number * factor) / factor;
    }

    valueOr(val: string, or: string) {
        if (!val) { return or; }
        return val;
    }

    sortRunByFitness(a: RunDetails, b: RunDetails) {
        if (a.fitness < b.fitness) {
            return -1;
        } else if (a.fitness > b.fitness) {
            return 1;
        }
        return 0;
    }

    processNewProgress(progress: TspTaskProgress) {
        const newConfigs = new Array<ConfigDetails>();
        for (let i = 0; i < this.configs.length; ++i) {
            const config = this.configs[i];

            for (let j = 0; j < config.runs.data.length; ++j) {
                const run = config.runs.data[j];
                const runProgress = progress.taskConfigRunProgress[run.id];
                if (runProgress !== undefined) {
                    run.status = this.getStatus(runProgress.status);
                    run.started = this.valueOr(runProgress.started, 'Not started');
                    run.completed = this.valueOr(runProgress.completed, 'Not completed');
                    run.fitness = runProgress.bestFitness;
                    run.progress = runProgress.progress * 100;
                    run.color = 'white';
                    config.runs.data[j] = run;
                }
            }
            config.runs.data = config.runs.data.sort(this.sortRunByFitness);

            const configProgress = progress.taskConfigProgress[config.id];
            if (configProgress !== undefined) {
                config.fitness = configProgress.bestFitness;
                config.progress = configProgress.progress * 100;
            }
            newConfigs.push(config);
        }
        this.configs = newConfigs;
        this.computeTime = this.msToTime(this.getComputeTime());
    }

    // timer
    startProgressTimer(interval: number) {
        this.unsubscribeProgressTimer();
        this.progressTimerSubscription = Observable.timer(interval).subscribe(
            () => this.loadProgress()
        );
    }

    unsubscribeProgressTimer() {
        if (this.progressTimerSubscription) {
            this.progressTimerSubscription.unsubscribe();
        }
    }

}


/*
processNewProgress(progress: TspTaskProgress) {
        const newConfigs = new Array<ConfigDetails>();
        for (let i = 0; i < this.configs.length; ++i) {
            const config = this.configs[i];
            let minFitness = Number.MAX_VALUE;
            let maxFitness = Number.MIN_VALUE;
            for (const run of config.runs.data) {
                const fitness = run.fitness;
                if (fitness > maxFitness) { maxFitness = fitness; }
                if (fitness < minFitness) { minFitness = fitness; }
            }

            for (let j = 0; j < config.runs.data.length; ++j) {
                const run = config.runs.data[j];
                const runProgress = progress.taskConfigRunProgress[run.id];
                if (runProgress !== undefined) {
                    run.status = this.getStatus(runProgress.status);
                    run.started = this.valueOr(runProgress.started, 'Not started');
                    run.completed = this.valueOr(runProgress.completed, 'Not completed');
                    run.fitness = runProgress.bestFitness;
                    run.progress = runProgress.progress * 100;
                    run.color = this.getRunColor(runProgress.status, run.fitness, minFitness, maxFitness);
                    config.runs.data[j] = run;
                }
            }
            const configRuns = config.runs.data.sort((a: any, b: any) => {
                if (a.fitness < b.fitness) {
                    return -1;
                } else if (a.fitness > b.fitness) {
                    return 1;
                } else {
                    return 0;
                }
            });
            config.runs.data = configRuns;

            const configProgress = progress.taskConfigProgress[config.id];
            if (configProgress !== undefined) {
                config.fitness = configProgress.bestFitness;
                config.progress = configProgress.progress * 100;
            }
            newConfigs.push(config);
        }
        this.configs = newConfigs;
    }
*/
