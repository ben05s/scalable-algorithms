import { TestBed, async } from '@angular/core/testing';
import { McDetailComponent } from './mc.detail.component';
describe('McDetailComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        McDetailComponent
      ],
    }).compileComponents();
  }));
  it('should create tsp detail', async(() => {
    const fixture = TestBed.createComponent(McDetailComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
