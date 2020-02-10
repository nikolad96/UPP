import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecenzentiComponent } from './recenzenti.component';

describe('RecenzentiComponent', () => {
  let component: RecenzentiComponent;
  let fixture: ComponentFixture<RecenzentiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecenzentiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecenzentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
