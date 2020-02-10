import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OdabirCasopisComponent } from './odabir-casopis.component';

describe('OdabirCasopisComponent', () => {
  let component: OdabirCasopisComponent;
  let fixture: ComponentFixture<OdabirCasopisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OdabirCasopisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OdabirCasopisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
