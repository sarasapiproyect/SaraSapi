import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../interations.test-samples';

import { InterationsFormService } from './interations-form.service';

describe('Interations Form Service', () => {
  let service: InterationsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InterationsFormService);
  });

  describe('Service methods', () => {
    describe('createInterationsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInterationsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valueRequest: expect.any(Object),
            sourceInfo: expect.any(Object),
            valueResponse: expect.any(Object),
            sourceChannel: expect.any(Object),
          })
        );
      });

      it('passing IInterations should create a new form with FormGroup', () => {
        const formGroup = service.createInterationsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valueRequest: expect.any(Object),
            sourceInfo: expect.any(Object),
            valueResponse: expect.any(Object),
            sourceChannel: expect.any(Object),
          })
        );
      });
    });

    describe('getInterations', () => {
      it('should return NewInterations for default Interations initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createInterationsFormGroup(sampleWithNewData);

        const interations = service.getInterations(formGroup) as any;

        expect(interations).toMatchObject(sampleWithNewData);
      });

      it('should return NewInterations for empty Interations initial value', () => {
        const formGroup = service.createInterationsFormGroup();

        const interations = service.getInterations(formGroup) as any;

        expect(interations).toMatchObject({});
      });

      it('should return IInterations', () => {
        const formGroup = service.createInterationsFormGroup(sampleWithRequiredData);

        const interations = service.getInterations(formGroup) as any;

        expect(interations).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInterations should not enable id FormControl', () => {
        const formGroup = service.createInterationsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInterations should disable id FormControl', () => {
        const formGroup = service.createInterationsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
