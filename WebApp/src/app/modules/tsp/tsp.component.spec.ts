import { TestBed, async } from '@angular/core/testing';
import { TspComponent } from './tsp.component';
describe('TspComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        TspComponent
      ],
    }).compileComponents();
  }));
  it('should create tsp', async(() => {
    const fixture = TestBed.createComponent(TspComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
