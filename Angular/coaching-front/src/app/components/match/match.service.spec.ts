import { NgModule } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { AnalysisComponent } from '../analysis/analysis.component';

import { MatchService } from './match.service';

describe('MatchService', () => {
  let service: MatchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MatchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

