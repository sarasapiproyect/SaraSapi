import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../chatboot-style.test-samples';

import { ChatbootStyleFormService } from './chatboot-style-form.service';

describe('ChatbootStyle Form Service', () => {
  let service: ChatbootStyleFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChatbootStyleFormService);
  });

  describe('Service methods', () => {
    describe('createChatbootStyleFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createChatbootStyleFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nameProperties: expect.any(Object),
            value: expect.any(Object),
            multimedia: expect.any(Object),
          })
        );
      });

      it('passing IChatbootStyle should create a new form with FormGroup', () => {
        const formGroup = service.createChatbootStyleFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nameProperties: expect.any(Object),
            value: expect.any(Object),
            multimedia: expect.any(Object),
          })
        );
      });
    });

    describe('getChatbootStyle', () => {
      it('should return NewChatbootStyle for default ChatbootStyle initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createChatbootStyleFormGroup(sampleWithNewData);

        const chatbootStyle = service.getChatbootStyle(formGroup) as any;

        expect(chatbootStyle).toMatchObject(sampleWithNewData);
      });

      it('should return NewChatbootStyle for empty ChatbootStyle initial value', () => {
        const formGroup = service.createChatbootStyleFormGroup();

        const chatbootStyle = service.getChatbootStyle(formGroup) as any;

        expect(chatbootStyle).toMatchObject({});
      });

      it('should return IChatbootStyle', () => {
        const formGroup = service.createChatbootStyleFormGroup(sampleWithRequiredData);

        const chatbootStyle = service.getChatbootStyle(formGroup) as any;

        expect(chatbootStyle).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IChatbootStyle should not enable id FormControl', () => {
        const formGroup = service.createChatbootStyleFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewChatbootStyle should disable id FormControl', () => {
        const formGroup = service.createChatbootStyleFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
