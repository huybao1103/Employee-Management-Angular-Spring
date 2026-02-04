import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToastsMessageComponent } from './toasts-message-component';

describe('ToastsMessageComponent', () => {
  let component: ToastsMessageComponent;
  let fixture: ComponentFixture<ToastsMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ToastsMessageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ToastsMessageComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
