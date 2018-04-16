import { TestBed, async } from '@angular/core/testing';
import { TspTasksComponent } from './tsp.tasks.component';
describe('TspTasksComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        TspTasksComponent
      ],
    }).compileComponents();
  }));
  it('should create tsp tasks', async(() => {
    const fixture = TestBed.createComponent(TspTasksComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
