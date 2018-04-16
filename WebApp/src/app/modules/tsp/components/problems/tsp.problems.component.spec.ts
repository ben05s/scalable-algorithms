import { TestBed, async } from '@angular/core/testing';
import { TspProblemsComponent } from './tsp.problems.component';
describe('TspProblemsComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        TspProblemsComponent
      ],
    }).compileComponents();
  }));
  it('should create TspProblemsComponent', async(() => {
    const fixture = TestBed.createComponent(TspProblemsComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
