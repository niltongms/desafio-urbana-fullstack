import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CartaoControleComponent } from './cartao-controle.component';

describe('CartaoControleComponent', () => {
  let component: CartaoControleComponent;
  let fixture: ComponentFixture<CartaoControleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CartaoControleComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CartaoControleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
