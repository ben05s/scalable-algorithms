import { TestBed, inject } from '@angular/core/testing';
import { McTasksService } from './mc.tasks.service';

describe('McTasksService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [McTasksService]
    });
  });

  it('should be created', inject([McTasksService], (service: McTasksService) => {
    expect(service).toBeTruthy();
  }));
});
