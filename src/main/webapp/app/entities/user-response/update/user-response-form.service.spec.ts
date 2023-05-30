import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../user-response.test-samples';

import { UserResponseFormService } from './user-response-form.service';

describe('UserResponse Form Service', () => {
  let service: UserResponseFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserResponseFormService);
  });

  describe('Service methods', () => {
    describe('createUserResponseFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUserResponseFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valueResponse: expect.any(Object),
            priority: expect.any(Object),
            multimedia: expect.any(Object),
            multimediaVoice: expect.any(Object),
            saraAnimation: expect.any(Object),
            isEndConversation: expect.any(Object),
            responseType: expect.any(Object),
            url: expect.any(Object),
            intents: expect.any(Object),
          })
        );
      });

      it('passing IUserResponse should create a new form with FormGroup', () => {
        const formGroup = service.createUserResponseFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valueResponse: expect.any(Object),
            priority: expect.any(Object),
            multimedia: expect.any(Object),
            multimediaVoice: expect.any(Object),
            saraAnimation: expect.any(Object),
            isEndConversation: expect.any(Object),
            responseType: expect.any(Object),
            url: expect.any(Object),
            intents: expect.any(Object),
          })
        );
      });
    });

    describe('getUserResponse', () => {
      it('should return NewUserResponse for default UserResponse initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUserResponseFormGroup(sampleWithNewData);

        const userResponse = service.getUserResponse(formGroup) as any;

        expect(userResponse).toMatchObject(sampleWithNewData);
      });

      it('should return NewUserResponse for empty UserResponse initial value', () => {
        const formGroup = service.createUserResponseFormGroup();

        const userResponse = service.getUserResponse(formGroup) as any;

        expect(userResponse).toMatchObject({});
      });

      it('should return IUserResponse', () => {
        const formGroup = service.createUserResponseFormGroup(sampleWithRequiredData);

        const userResponse = service.getUserResponse(formGroup) as any;

        expect(userResponse).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUserResponse should not enable id FormControl', () => {
        const formGroup = service.createUserResponseFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUserResponse should disable id FormControl', () => {
        const formGroup = service.createUserResponseFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
