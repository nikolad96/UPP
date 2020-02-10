import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BrojRecComponent } from './broj-rec.component';

describe('BrojRecComponent', () => {
  let component: BrojRecComponent;
  let fixture: ComponentFixture<BrojRecComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BrojRecComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BrojRecComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
