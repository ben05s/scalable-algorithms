import { TestBed, async } from '@angular/core/testing';
import { McComponent } from './mc.component';
describe('McComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        McComponent
      ],
    }).compileComponents();
  }));
  it('should create mc', async(() => {
    const fixture = TestBed.createComponent(McComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
