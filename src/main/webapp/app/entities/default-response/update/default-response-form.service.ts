import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDefaultResponse, NewDefaultResponse } from '../default-response.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDefaultResponse for edit and NewDefaultResponseFormGroupInput for create.
 */
type DefaultResponseFormGroupInput = IDefaultResponse | PartialWithRequiredKeyOf<NewDefaultResponse>;

type DefaultResponseFormDefaults = Pick<NewDefaultResponse, 'id' | 'isEndConversation'>;

type DefaultResponseFormGroupContent = {
  id: FormControl<IDefaultResponse['id'] | NewDefaultResponse['id']>;
  defaultValueResponse: FormControl<IDefaultResponse['defaultValueResponse']>;
  priority: FormControl<IDefaultResponse['priority']>;
  multimedia: FormControl<IDefaultResponse['multimedia']>;
  multimediaContentType: FormControl<IDefaultResponse['multimediaContentType']>;
  multimediaVoice: FormControl<IDefaultResponse['multimediaVoice']>;
  multimediaVoiceContentType: FormControl<IDefaultResponse['multimediaVoiceContentType']>;
  saraAnimation: FormControl<IDefaultResponse['saraAnimation']>;
  saraAnimationContentType: FormControl<IDefaultResponse['saraAnimationContentType']>;
  isEndConversation: FormControl<IDefaultResponse['isEndConversation']>;
  multimediaUrl: FormControl<IDefaultResponse['multimediaUrl']>;
  multimediaVoiceUrl: FormControl<IDefaultResponse['multimediaVoiceUrl']>;
  saraAnimationUrl: FormControl<IDefaultResponse['saraAnimationUrl']>;
};

export type DefaultResponseFormGroup = FormGroup<DefaultResponseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DefaultResponseFormService {
  createDefaultResponseFormGroup(defaultResponse: DefaultResponseFormGroupInput = { id: null }): DefaultResponseFormGroup {
    const defaultResponseRawValue = {
      ...this.getFormDefaults(),
      ...defaultResponse,
    };
    return new FormGroup<DefaultResponseFormGroupContent>({
      id: new FormControl(
        { value: defaultResponseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      defaultValueResponse: new FormControl(defaultResponseRawValue.defaultValueResponse, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(500)],
      }),
      priority: new FormControl(defaultResponseRawValue.priority),
      multimedia: new FormControl(defaultResponseRawValue.multimedia),
      multimediaContentType: new FormControl(defaultResponseRawValue.multimediaContentType),
      multimediaVoice: new FormControl(defaultResponseRawValue.multimediaVoice),
      multimediaVoiceContentType: new FormControl(defaultResponseRawValue.multimediaVoiceContentType),
      saraAnimation: new FormControl(defaultResponseRawValue.saraAnimation),
      saraAnimationContentType: new FormControl(defaultResponseRawValue.saraAnimationContentType),
      isEndConversation: new FormControl(defaultResponseRawValue.isEndConversation),
      multimediaUrl: new FormControl(defaultResponseRawValue.multimediaUrl),
      multimediaVoiceUrl: new FormControl(defaultResponseRawValue.multimediaVoiceUrl),
      saraAnimationUrl: new FormControl(defaultResponseRawValue.saraAnimationUrl),
    });
  }

  getDefaultResponse(form: DefaultResponseFormGroup): IDefaultResponse | NewDefaultResponse {
    return form.getRawValue() as IDefaultResponse | NewDefaultResponse;
  }

  resetForm(form: DefaultResponseFormGroup, defaultResponse: DefaultResponseFormGroupInput): void {
    const defaultResponseRawValue = { ...this.getFormDefaults(), ...defaultResponse };
    form.reset(
      {
        ...defaultResponseRawValue,
        id: { value: defaultResponseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DefaultResponseFormDefaults {
    return {
      id: null,
      isEndConversation: false,
    };
  }
}
