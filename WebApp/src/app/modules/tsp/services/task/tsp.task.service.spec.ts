import { TestBed, inject } from '@angular/core/testing';
import { TspTaskService } from './tsp.task.service';

describe('TspTaskService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TspTaskService]
    });
  });

  it('should be created', inject([TspTaskService], (service: TspTaskService) => {
    expect(service).toBeTruthy();
  }));
});
