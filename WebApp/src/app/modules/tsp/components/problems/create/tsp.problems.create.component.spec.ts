import { TestBed, async } from '@angular/core/testing';
import { TspProblemsCreateComponent } from './tsp.problems.create.component';
describe('TspProblemsCreateComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        TspProblemsCreateComponent
      ],
    }).compileComponents();
  }));
  it('should create TspProblemsCreateComponent', async(() => {
    const fixture = TestBed.createComponent(TspProblemsCreateComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
