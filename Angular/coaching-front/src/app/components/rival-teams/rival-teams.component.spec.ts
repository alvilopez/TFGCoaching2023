import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RivalTeamsComponent } from './rival-teams.component';

describe('RivalTeamsComponent', () => {
  let component: RivalTeamsComponent;
  let fixture: ComponentFixture<RivalTeamsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RivalTeamsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RivalTeamsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
