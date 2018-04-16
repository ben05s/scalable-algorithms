import { TestBed, async } from '@angular/core/testing';
import { TspTasksDetailsComponent } from './tsp.tasks.details.component';
describe('TspTasksDetailsComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        TspTasksDetailsComponent
      ],
    }).compileComponents();
  }));
  it('should create TspTasksDetailsComponent', async(() => {
    const fixture = TestBed.createComponent(TspTasksDetailsComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
