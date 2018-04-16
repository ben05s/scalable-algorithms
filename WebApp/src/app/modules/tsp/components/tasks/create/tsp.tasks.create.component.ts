import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { Router } from '@angular/router';

import { TspCity } from '../../../models/TspCity';
import { TspProblem } from '../../../models/TspProblem';

import { TspProblemService } from '../../../services/problem/tsp.problem.service';
import { TspTaskService } from '../../../services/task/tsp.task.service';

@Component({
  templateUrl: './tsp.tasks.create.component.html',
  styleUrls: ['./tsp.tasks.create.component.css']
})
export class TspTasksCreateComponent implements OnInit {
    name: string;
    _problemIndex = -1;
    problems: TspProblem[] = new Array<TspProblem>();
    problemMap: Map<number, TspProblem> = new Map<number, TspProblem>();
    cities: TspCity[] = new Array<TspCity>();

    get problemIndex() {
        return this._problemIndex;
    }

    set problemIndex(id: number) {
        this._problemIndex = id;
        console.log('new problem id', id);
        if (id >= 0) {
            this.cities = this.problems[id].cities;
        }
    }

    constructor(
        private _router: Router,
        private _dialogRef: MatDialogRef<TspTasksCreateComponent>,
        private _problemService: TspProblemService,
        private _taskService: TspTaskService) {}

    ngOnInit(): void {
        this.loadProblems();
    }

    closeDialog(id: number): void {
        this._dialogRef.close(id);
    }

    loadProblems(): void {
        this._problemService.getProblems().subscribe(
            problems => {
                this.problems = problems;
            }
        );
    }
    addTaskRequest(): void {
        this._taskService.addTask({
            name: this.name,
            problemId: this.problems[this.problemIndex].id
        }).subscribe(
            response => {
                console.log('Added tasks:', response);
                this.closeDialog(response.id);
                this._router.navigate(['tsp/tasks', response.id]);
            }, error => {
                console.log('Error adding task:', error);
            }
        );
    }
}
