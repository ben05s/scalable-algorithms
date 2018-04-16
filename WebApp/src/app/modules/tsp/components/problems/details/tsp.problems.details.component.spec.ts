import { TestBed, async } from '@angular/core/testing';
import { TspProblemsDetailsComponent } from './tsp.problems.details.component';
describe('TspProblemsDetailsComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        TspProblemsDetailsComponent
      ],
    }).compileComponents();
  }));
  it('should create TspProblemsDetailsComponent', async(() => {
    const fixture = TestBed.createComponent(TspProblemsDetailsComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
