import { TestBed, async } from '@angular/core/testing';
import { McSettingsComponent } from './mc.settings.component';
describe('McSettingsComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        McSettingsComponent
      ],
    }).compileComponents();
  }));
  it('should create tsp settings', async(() => {
    const fixture = TestBed.createComponent(McSettingsComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
