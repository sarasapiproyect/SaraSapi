import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IChatbootStyle, NewChatbootStyle } from '../chatboot-style.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IChatbootStyle for edit and NewChatbootStyleFormGroupInput for create.
 */
type ChatbootStyleFormGroupInput = IChatbootStyle | PartialWithRequiredKeyOf<NewChatbootStyle>;

type ChatbootStyleFormDefaults = Pick<NewChatbootStyle, 'id'>;

type ChatbootStyleFormGroupContent = {
  id: FormControl<IChatbootStyle['id'] | NewChatbootStyle['id']>;
  nameProperties: FormControl<IChatbootStyle['nameProperties']>;
  value: FormControl<IChatbootStyle['value']>;
  multimedia: FormControl<IChatbootStyle['multimedia']>;
  multimediaContentType: FormControl<IChatbootStyle['multimediaContentType']>;
};

export type ChatbootStyleFormGroup = FormGroup<ChatbootStyleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ChatbootStyleFormService {
  createChatbootStyleFormGroup(chatbootStyle: ChatbootStyleFormGroupInput = { id: null }): ChatbootStyleFormGroup {
    const chatbootStyleRawValue = {
      ...this.getFormDefaults(),
      ...chatbootStyle,
    };
    return new FormGroup<ChatbootStyleFormGroupContent>({
      id: new FormControl(
        { value: chatbootStyleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nameProperties: new FormControl(chatbootStyleRawValue.nameProperties, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(300)],
      }),
      value: new FormControl(chatbootStyleRawValue.value, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(300)],
      }),
      multimedia: new FormControl(chatbootStyleRawValue.multimedia),
      multimediaContentType: new FormControl(chatbootStyleRawValue.multimediaContentType),
    });
  }

  getChatbootStyle(form: ChatbootStyleFormGroup): IChatbootStyle | NewChatbootStyle {
    return form.getRawValue() as IChatbootStyle | NewChatbootStyle;
  }

  resetForm(form: ChatbootStyleFormGroup, chatbootStyle: ChatbootStyleFormGroupInput): void {
    const chatbootStyleRawValue = { ...this.getFormDefaults(), ...chatbootStyle };
    form.reset(
      {
        ...chatbootStyleRawValue,
        id: { value: chatbootStyleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ChatbootStyleFormDefaults {
    return {
      id: null,
    };
  }
}
