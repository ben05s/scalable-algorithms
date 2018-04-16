import { Component, OnInit, OnDestroy, ViewChild, Input } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { ActivatedRoute } from '@angular/router';
// models
import { TspCity } from '../../../models/TspCity';
import { TspProblem } from '../../../models/TspProblem';
// services
import { TspProblemService } from '../../../services/problem/tsp.problem.service';

@Component({
  selector: 'tsp-problems-details',
  templateUrl: './tsp.problems.details.component.html',
  styleUrls: ['./tsp.problems.details.component.css']
})
export class TspProblemsDetailsComponent implements OnInit, OnDestroy {

    dataColumns = ['x', 'y'];
    dataSource = new MatTableDataSource<TspCity>();

    _problemId = 0;
    problem: TspProblem = new TspProblem();

    tour: number[] = [];
    cities: TspCity[] = new Array<TspCity>();

    precisionRound(number, precision) {
        const factor = Math.pow(10, precision);
        return Math.round(number * factor) / factor;
    }

    get problemId(): number {
        return this._problemId;
    }

    @Input()
    set problemId(problemId: number) {
        this._problemId = problemId;
        this.loadProblemDetails();
    }

    constructor(private _problemService: TspProblemService,
                private route: ActivatedRoute) {
        this.route.params.subscribe(params => {
            this.problemId = params.problemId;
        });
    }

    ngOnDestroy(): void {}
    ngOnInit(): void {}

    private loadProblemDetails(): void {
        if (this._problemId <= 0) { return; }
        this._problemService.getProblem(this._problemId).subscribe(problem => {
            this.problem = problem;
            this.dataSource = new MatTableDataSource<TspCity>(this.problem.cities);
        });
    }
}
