import { TestBed, async } from '@angular/core/testing';
import { McTasksComponent } from './mc.tasks.component';

describe('McTasksComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        McTasksComponent
      ],
    }).compileComponents();
  }));
  it('should create tsp tasks', async(() => {
    const fixture = TestBed.createComponent(McTasksComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
