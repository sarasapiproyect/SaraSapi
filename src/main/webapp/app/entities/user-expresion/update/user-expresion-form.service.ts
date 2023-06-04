import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUserExpresion, NewUserExpresion } from '../user-expresion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUserExpresion for edit and NewUserExpresionFormGroupInput for create.
 */
type UserExpresionFormGroupInput = IUserExpresion | PartialWithRequiredKeyOf<NewUserExpresion>;

type UserExpresionFormDefaults = Pick<NewUserExpresion, 'id' | 'intents'>;

type UserExpresionFormGroupContent = {
  id: FormControl<IUserExpresion['id'] | NewUserExpresion['id']>;
  value: FormControl<IUserExpresion['value']>;
  priority: FormControl<IUserExpresion['priority']>;
  intents: FormControl<IUserExpresion['intents']>;
};

export type UserExpresionFormGroup = FormGroup<UserExpresionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UserExpresionFormService {
  createUserExpresionFormGroup(userExpresion: UserExpresionFormGroupInput = { id: null }): UserExpresionFormGroup {
    const userExpresionRawValue = {
      ...this.getFormDefaults(),
      ...userExpresion,
    };
    return new FormGroup<UserExpresionFormGroupContent>({
      id: new FormControl(
        { value: userExpresionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      value: new FormControl(userExpresionRawValue.value, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(2000)],
      }),
      priority: new FormControl(userExpresionRawValue.priority),
      intents: new FormControl(userExpresionRawValue.intents ?? []),
    });
  }

  getUserExpresion(form: UserExpresionFormGroup): IUserExpresion | NewUserExpresion {
    return form.getRawValue() as IUserExpresion | NewUserExpresion;
  }

  resetForm(form: UserExpresionFormGroup, userExpresion: UserExpresionFormGroupInput): void {
    const userExpresionRawValue = { ...this.getFormDefaults(), ...userExpresion };
    form.reset(
      {
        ...userExpresionRawValue,
        id: { value: userExpresionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UserExpresionFormDefaults {
    return {
      id: null,
      intents: [],
    };
  }
}
