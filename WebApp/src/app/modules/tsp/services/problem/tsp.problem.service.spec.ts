import { TestBed, inject } from '@angular/core/testing';
import { TspProblemService } from './tsp.problem.service';

describe('TspProblemService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TspProblemService]
    });
  });

  it('should be created', inject([TspProblemService], (service: TspProblemService) => {
    expect(service).toBeTruthy();
  }));
});
