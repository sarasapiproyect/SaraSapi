import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IIntent, NewIntent } from '../intent.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIntent for edit and NewIntentFormGroupInput for create.
 */
type IntentFormGroupInput = IIntent | PartialWithRequiredKeyOf<NewIntent>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IIntent | NewIntent> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

type IntentFormRawValue = FormValueOf<IIntent>;

type NewIntentFormRawValue = FormValueOf<NewIntent>;

type IntentFormDefaults = Pick<NewIntent, 'id' | 'enabled' | 'creationDate' | 'userExpresions' | 'userResponses'>;

type IntentFormGroupContent = {
  id: FormControl<IntentFormRawValue['id'] | NewIntent['id']>;
  intenType: FormControl<IntentFormRawValue['intenType']>;
  name: FormControl<IntentFormRawValue['name']>;
  description: FormControl<IntentFormRawValue['description']>;
  urlRequest: FormControl<IntentFormRawValue['urlRequest']>;
  enabled: FormControl<IntentFormRawValue['enabled']>;
  creationDate: FormControl<IntentFormRawValue['creationDate']>;
  languaje: FormControl<IntentFormRawValue['languaje']>;
  userExpresions: FormControl<IntentFormRawValue['userExpresions']>;
  userResponses: FormControl<IntentFormRawValue['userResponses']>;
};

export type IntentFormGroup = FormGroup<IntentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IntentFormService {
  createIntentFormGroup(intent: IntentFormGroupInput = { id: null }): IntentFormGroup {
    const intentRawValue = this.convertIntentToIntentRawValue({
      ...this.getFormDefaults(),
      ...intent,
    });
    return new FormGroup<IntentFormGroupContent>({
      id: new FormControl(
        { value: intentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      intenType: new FormControl(intentRawValue.intenType),
      name: new FormControl(intentRawValue.name, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(300)],
      }),
      description: new FormControl(intentRawValue.description, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(300)],
      }),
      urlRequest: new FormControl(intentRawValue.urlRequest, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(300)],
      }),
      enabled: new FormControl(intentRawValue.enabled),
      creationDate: new FormControl(intentRawValue.creationDate),
      languaje: new FormControl(intentRawValue.languaje),
      userExpresions: new FormControl(intentRawValue.userExpresions ?? []),
      userResponses: new FormControl(intentRawValue.userResponses ?? []),
    });
  }

  getIntent(form: IntentFormGroup): IIntent | NewIntent {
    return this.convertIntentRawValueToIntent(form.getRawValue() as IntentFormRawValue | NewIntentFormRawValue);
  }

  resetForm(form: IntentFormGroup, intent: IntentFormGroupInput): void {
    const intentRawValue = this.convertIntentToIntentRawValue({ ...this.getFormDefaults(), ...intent });
    form.reset(
      {
        ...intentRawValue,
        id: { value: intentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IntentFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      enabled: false,
      creationDate: currentTime,
      userExpresions: [],
      userResponses: [],
    };
  }

  private convertIntentRawValueToIntent(rawIntent: IntentFormRawValue | NewIntentFormRawValue): IIntent | NewIntent {
    return {
      ...rawIntent,
      creationDate: dayjs(rawIntent.creationDate, DATE_TIME_FORMAT),
    };
  }

  private convertIntentToIntentRawValue(
    intent: IIntent | (Partial<NewIntent> & IntentFormDefaults)
  ): IntentFormRawValue | PartialWithRequiredKeyOf<NewIntentFormRawValue> {
    return {
      ...intent,
      creationDate: intent.creationDate ? intent.creationDate.format(DATE_TIME_FORMAT) : undefined,
      userExpresions: intent.userExpresions ?? [],
      userResponses: intent.userResponses ?? [],
    };
  }
}
