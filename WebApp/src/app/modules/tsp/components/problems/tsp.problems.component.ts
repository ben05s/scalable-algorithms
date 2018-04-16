import { Component, OnInit, OnDestroy, ViewChild, EventEmitter, Output } from '@angular/core';
import { MatDialog } from '@angular/material';
import { TspProblem } from '../../models/TspProblem';
import { Subscription } from 'rxjs/Subscription';
import { TspProblemService } from '../../services/problem/tsp.problem.service';
import { TspProblemsCreateComponent } from './create/tsp.problems.create.component';

@Component({
  selector: 'tsp-problems',
  templateUrl: './tsp.problems.component.html',
  styleUrls: ['./tsp.problems.component.css']
})
export class TspProblemsComponent implements OnInit, OnDestroy {
    @Output() selectedProblem = new EventEmitter<number>();

    selectedProblemId = 0;
    isLoading = false;

    problems: TspProblem[] = new Array<TspProblem>();
    problemsSubscription: Subscription;


    constructor(private _dialog: MatDialog,
                private _problemService: TspProblemService) {}

    ngOnInit() {
        this.loadProblems();
    }

    ngOnDestroy() {
        this.unsubscribeProblems();
    }

    openAddProblemDialog(): void {
        const dialogRef = this._dialog.open(TspProblemsCreateComponent, {
            width: '450px'
        });

        dialogRef.afterClosed().subscribe(id => {
            // if (id) {
            //   this.loadProblem(id);
            // }
        });
    }

    loadProblems(): void {
        this.isLoading = true;
        this.unsubscribeProblems();
        this.problemsSubscription = this._problemService.getProblems().subscribe(
            problemsResponse => {
                this.isLoading = false;
                this.problems = problemsResponse;
            },
            error => {
                this.isLoading = false;
                this.problems = new Array<TspProblem>();
            }
        );
    }

    loadProblem(id: number): void {
        this.isLoading = true;
        this.unsubscribeProblems();
        this.problemsSubscription = this._problemService.getProblem(id).subscribe(
            problemResponse => {
                this.isLoading = false;
                this.problems.push(problemResponse);
            },
            error => {
                this.isLoading = false;
            }
        );
    }

    private unsubscribeProblems() {
        if (this.problemsSubscription) {
            this.problemsSubscription.unsubscribe();
        }
    }

    onSelectedProblem(id: number) {
        this.selectedProblemId = id;
        this.selectedProblem.emit(id);
    }
}
