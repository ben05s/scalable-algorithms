import { TestBed, async } from '@angular/core/testing';
import { TspTourDiagramComponent } from './tsp.tour.diagram.component';
describe('TspTourDiagramComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        TspTourDiagramComponent
      ],
    }).compileComponents();
  }));
  it('should create TspTourDiagramComponent', async(() => {
    const fixture = TestBed.createComponent(TspTourDiagramComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
