import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormatiranComponent } from './formatiran.component';

describe('FormatiranComponent', () => {
  let component: FormatiranComponent;
  let fixture: ComponentFixture<FormatiranComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormatiranComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormatiranComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
