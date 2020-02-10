import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Provera1Component } from './provera1.component';

describe('Provera1Component', () => {
  let component: Provera1Component;
  let fixture: ComponentFixture<Provera1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Provera1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Provera1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
