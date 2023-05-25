import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../user-expresion.test-samples';

import { UserExpresionFormService } from './user-expresion-form.service';

describe('UserExpresion Form Service', () => {
  let service: UserExpresionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserExpresionFormService);
  });

  describe('Service methods', () => {
    describe('createUserExpresionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUserExpresionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            value: expect.any(Object),
            priority: expect.any(Object),
            intents: expect.any(Object),
          })
        );
      });

      it('passing IUserExpresion should create a new form with FormGroup', () => {
        const formGroup = service.createUserExpresionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            value: expect.any(Object),
            priority: expect.any(Object),
            intents: expect.any(Object),
          })
        );
      });
    });

    describe('getUserExpresion', () => {
      it('should return NewUserExpresion for default UserExpresion initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUserExpresionFormGroup(sampleWithNewData);

        const userExpresion = service.getUserExpresion(formGroup) as any;

        expect(userExpresion).toMatchObject(sampleWithNewData);
      });

      it('should return NewUserExpresion for empty UserExpresion initial value', () => {
        const formGroup = service.createUserExpresionFormGroup();

        const userExpresion = service.getUserExpresion(formGroup) as any;

        expect(userExpresion).toMatchObject({});
      });

      it('should return IUserExpresion', () => {
        const formGroup = service.createUserExpresionFormGroup(sampleWithRequiredData);

        const userExpresion = service.getUserExpresion(formGroup) as any;

        expect(userExpresion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUserExpresion should not enable id FormControl', () => {
        const formGroup = service.createUserExpresionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUserExpresion should disable id FormControl', () => {
        const formGroup = service.createUserExpresionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
