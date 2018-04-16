import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Point } from './models/Point';

@Component({
  selector: 'tsp-tour-diagram',
  templateUrl: './tsp.tour.diagram.component.html',
  styleUrls: ['./tsp.tour.diagram.component.css']
})
export class TspTourDiagramComponent implements OnInit {
  _cities: Point[] = [];
  _tour: number[] = [];
  tourPath: Point[][] = new Array<Array<Point>>();
  normalizedCities: Point[] = new Array<Point>();

  constructor() {}

  ngOnInit() {
    this.updateTourCoords();
    this.normalizeCities();
  }

  public get cities(): Point[] {
    return this._cities;
  }

  @Input()
  public set cities(cities: Point[]) {
    this._cities = cities;
    this.normalizeCities();
  }

  public get tour(): number[] {
    return this._tour;
  }

  @Input()
  public set tour(tour: number[]) {
    this._tour = tour;
    this.updateTourCoords();
  }

  private updateTourCoords() {
    if (this.normalizedCities.length <= 0) { return; }
    const tour = this.tour;
    const length = tour.length;
    const coords = new Array<Array<Point>>();
    if (length > 1) {
      for (let i = 0; i < length - 1; ++i) {
        const points = new Array<Point>();
        points.push(this.normalizedCities[tour[i]]);
        points.push(this.normalizedCities[tour[i + 1]]);
        coords.push(points);
      }
      const last = new Array<Point>();
      last.push(this.normalizedCities[tour[0]]);
      last.push(this.normalizedCities[tour[length - 1]]);
      coords.push(last);
    }
    this.tourPath = coords;
  }

  private normalizeCities() {
    // the viewport is 0 to 1
    // 0.05 to 0.95 gives us a small border
    const min = 0.05;
    const max = 0.95;
    const cities = this.cities;
    const noOfCities = cities.length;
    if (noOfCities > 0) {
      const normCities = new Array<Point>(noOfCities);
      const scale = max - min;
      let dataMin, dataMax;
      dataMin = dataMax = cities[0].x;
      for (let i = 0; i < noOfCities; ++i) {
        dataMin = Math.min(dataMin, cities[i].x, cities[i].y);
        dataMax = Math.max(dataMax, cities[i].x, cities[i].y);
      }
      // scale data
      const dataScale = dataMax - dataMin;
      for (let i = 0; i < noOfCities; ++i) {
        normCities[i] = new Point(
          scale * (cities[i].x - dataMin) / dataScale + min,
          scale * (cities[i].y - dataMin) / dataScale + min
        );
      }
      this.normalizedCities = normCities;
    }
  }
}
