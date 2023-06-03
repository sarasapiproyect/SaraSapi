import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../default-response.test-samples';

import { DefaultResponseFormService } from './default-response-form.service';

describe('DefaultResponse Form Service', () => {
  let service: DefaultResponseFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DefaultResponseFormService);
  });

  describe('Service methods', () => {
    describe('createDefaultResponseFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDefaultResponseFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            defaultValueResponse: expect.any(Object),
            priority: expect.any(Object),
            multimedia: expect.any(Object),
            multimediaVoice: expect.any(Object),
            saraAnimation: expect.any(Object),
            isEndConversation: expect.any(Object),
            multimediaUrl: expect.any(Object),
            multimediaVoiceUrl: expect.any(Object),
            saraAnimationUrl: expect.any(Object),
            multimediaType: expect.any(Object),
          })
        );
      });

      it('passing IDefaultResponse should create a new form with FormGroup', () => {
        const formGroup = service.createDefaultResponseFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            defaultValueResponse: expect.any(Object),
            priority: expect.any(Object),
            multimedia: expect.any(Object),
            multimediaVoice: expect.any(Object),
            saraAnimation: expect.any(Object),
            isEndConversation: expect.any(Object),
            multimediaUrl: expect.any(Object),
            multimediaVoiceUrl: expect.any(Object),
            saraAnimationUrl: expect.any(Object),
            multimediaType: expect.any(Object),
          })
        );
      });
    });

    describe('getDefaultResponse', () => {
      it('should return NewDefaultResponse for default DefaultResponse initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDefaultResponseFormGroup(sampleWithNewData);

        const defaultResponse = service.getDefaultResponse(formGroup) as any;

        expect(defaultResponse).toMatchObject(sampleWithNewData);
      });

      it('should return NewDefaultResponse for empty DefaultResponse initial value', () => {
        const formGroup = service.createDefaultResponseFormGroup();

        const defaultResponse = service.getDefaultResponse(formGroup) as any;

        expect(defaultResponse).toMatchObject({});
      });

      it('should return IDefaultResponse', () => {
        const formGroup = service.createDefaultResponseFormGroup(sampleWithRequiredData);

        const defaultResponse = service.getDefaultResponse(formGroup) as any;

        expect(defaultResponse).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDefaultResponse should not enable id FormControl', () => {
        const formGroup = service.createDefaultResponseFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDefaultResponse should disable id FormControl', () => {
        const formGroup = service.createDefaultResponseFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
