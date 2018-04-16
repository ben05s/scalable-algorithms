import { TestBed, inject } from '@angular/core/testing';
import { TspOperatorsService } from './tsp.operators.service';

describe('TspOperatorsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TspOperatorsService]
    });
  });

  it('should be created', inject([TspOperatorsService], (service: TspOperatorsService) => {
    expect(service).toBeTruthy();
  }));
});
