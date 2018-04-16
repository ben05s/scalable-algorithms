import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { TspProblemService } from '../../../services/problem/tsp.problem.service';
import { TspCity } from '../../../models/TspCity';
import { Router } from '@angular/router';
import { TspAddProblemRequest } from '../../../services/requests/TspAddProblemRequest';

@Component({
  templateUrl: './tsp.problems.create.component.html',
  styleUrls: ['./tsp.problems.create.component.css']
})
export class TspProblemsCreateComponent implements OnInit {
    name: string;
    _noOfCities = 25;
    cities: TspCity[] = new Array<TspCity>();
    tour: number[] = [];
    response: string;

    get noOfCities(): number {
        return this._noOfCities;
    }

    @Input()
    set noOfCities(noOfCities: number) {
        this._noOfCities = noOfCities;
        this.randomizeCities();
    }

    constructor(
        private _router: Router,
        private _dialogRef: MatDialogRef<TspProblemsCreateComponent>,
        private _problemService: TspProblemService) {}

    ngOnInit(): void {
        this.randomizeCities();
    }

    closeDialog(id: number): void {
        this._dialogRef.close(id);
    }

    randomizeCities(): void {
        const cities = new Array<TspCity>();
        for (let i = 0; i < this.noOfCities; ++i) {
            cities.push({
                x: Math.random(),
                y: Math.random()
            });
        }
        this.cities = cities;
    }

    addProblemRequest(): void {
        this._problemService.addProblem({
            name: this.name,
            cities: this.cities
        }).subscribe(
            response => {
                console.log('Added problem:', response);
                this.closeDialog(response.id);
                this._router.navigate(['tsp/problems', response.id]);
            }, error => {
                console.log('Error adding problem:', error);
            }
        );
    }
}
