import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUserResponse, NewUserResponse } from '../user-response.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUserResponse for edit and NewUserResponseFormGroupInput for create.
 */
type UserResponseFormGroupInput = IUserResponse | PartialWithRequiredKeyOf<NewUserResponse>;

type UserResponseFormDefaults = Pick<NewUserResponse, 'id' | 'isEndConversation' | 'intents'>;

type UserResponseFormGroupContent = {
  id: FormControl<IUserResponse['id'] | NewUserResponse['id']>;
  valueResponse: FormControl<IUserResponse['valueResponse']>;
  priority: FormControl<IUserResponse['priority']>;
  multimedia: FormControl<IUserResponse['multimedia']>;
  multimediaContentType: FormControl<IUserResponse['multimediaContentType']>;
  multimediaVoice: FormControl<IUserResponse['multimediaVoice']>;
  multimediaVoiceContentType: FormControl<IUserResponse['multimediaVoiceContentType']>;
  saraAnimation: FormControl<IUserResponse['saraAnimation']>;
  saraAnimationContentType: FormControl<IUserResponse['saraAnimationContentType']>;
  isEndConversation: FormControl<IUserResponse['isEndConversation']>;
  responseType: FormControl<IUserResponse['responseType']>;
  url: FormControl<IUserResponse['url']>;
  multimediaUrl: FormControl<IUserResponse['multimediaUrl']>;
  multimediaVoiceUrl: FormControl<IUserResponse['multimediaVoiceUrl']>;
  saraAnimationUrl: FormControl<IUserResponse['saraAnimationUrl']>;
  intents: FormControl<IUserResponse['intents']>;
};

export type UserResponseFormGroup = FormGroup<UserResponseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UserResponseFormService {
  createUserResponseFormGroup(userResponse: UserResponseFormGroupInput = { id: null }): UserResponseFormGroup {
    const userResponseRawValue = {
      ...this.getFormDefaults(),
      ...userResponse,
    };
    return new FormGroup<UserResponseFormGroupContent>({
      id: new FormControl(
        { value: userResponseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      valueResponse: new FormControl(userResponseRawValue.valueResponse, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(300)],
      }),
      priority: new FormControl(userResponseRawValue.priority),
      multimedia: new FormControl(userResponseRawValue.multimedia),
      multimediaContentType: new FormControl(userResponseRawValue.multimediaContentType),
      multimediaVoice: new FormControl(userResponseRawValue.multimediaVoice),
      multimediaVoiceContentType: new FormControl(userResponseRawValue.multimediaVoiceContentType),
      saraAnimation: new FormControl(userResponseRawValue.saraAnimation),
      saraAnimationContentType: new FormControl(userResponseRawValue.saraAnimationContentType),
      isEndConversation: new FormControl(userResponseRawValue.isEndConversation),
      responseType: new FormControl(userResponseRawValue.responseType),
      url: new FormControl(userResponseRawValue.url),
      multimediaUrl: new FormControl(userResponseRawValue.multimediaUrl),
      multimediaVoiceUrl: new FormControl(userResponseRawValue.multimediaVoiceUrl),
      saraAnimationUrl: new FormControl(userResponseRawValue.saraAnimationUrl),
      intents: new FormControl(userResponseRawValue.intents ?? []),
    });
  }

  getUserResponse(form: UserResponseFormGroup): IUserResponse | NewUserResponse {
    return form.getRawValue() as IUserResponse | NewUserResponse;
  }

  resetForm(form: UserResponseFormGroup, userResponse: UserResponseFormGroupInput): void {
    const userResponseRawValue = { ...this.getFormDefaults(), ...userResponse };
    form.reset(
      {
        ...userResponseRawValue,
        id: { value: userResponseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UserResponseFormDefaults {
    return {
      id: null,
      isEndConversation: false,
      intents: [],
    };
  }
}
