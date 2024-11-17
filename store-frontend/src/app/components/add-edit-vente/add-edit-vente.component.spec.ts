import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditVenteComponent } from './add-edit-vente.component';

describe('AddEditVenteComponent', () => {
  let component: AddEditVenteComponent;
  let fixture: ComponentFixture<AddEditVenteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddEditVenteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditVenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
