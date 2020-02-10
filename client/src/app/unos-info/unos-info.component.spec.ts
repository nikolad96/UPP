import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnosInfoComponent } from './unos-info.component';

describe('UnosInfoComponent', () => {
  let component: UnosInfoComponent;
  let fixture: ComponentFixture<UnosInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnosInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnosInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
