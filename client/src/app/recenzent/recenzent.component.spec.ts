import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecenzentComponent } from './recenzent.component';

describe('RecenzentComponent', () => {
  let component: RecenzentComponent;
  let fixture: ComponentFixture<RecenzentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecenzentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecenzentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
