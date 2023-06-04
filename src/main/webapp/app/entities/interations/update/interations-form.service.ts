import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInterations, NewInterations } from '../interations.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInterations for edit and NewInterationsFormGroupInput for create.
 */
type InterationsFormGroupInput = IInterations | PartialWithRequiredKeyOf<NewInterations>;

type InterationsFormDefaults = Pick<NewInterations, 'id'>;

type InterationsFormGroupContent = {
  id: FormControl<IInterations['id'] | NewInterations['id']>;
  valueRequest: FormControl<IInterations['valueRequest']>;
  sourceInfo: FormControl<IInterations['sourceInfo']>;
  valueResponse: FormControl<IInterations['valueResponse']>;
  sourceChannel: FormControl<IInterations['sourceChannel']>;
};

export type InterationsFormGroup = FormGroup<InterationsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InterationsFormService {
  createInterationsFormGroup(interations: InterationsFormGroupInput = { id: null }): InterationsFormGroup {
    const interationsRawValue = {
      ...this.getFormDefaults(),
      ...interations,
    };
    return new FormGroup<InterationsFormGroupContent>({
      id: new FormControl(
        { value: interationsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      valueRequest: new FormControl(interationsRawValue.valueRequest, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(300)],
      }),
      sourceInfo: new FormControl(interationsRawValue.sourceInfo, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(300)],
      }),
      valueResponse: new FormControl(interationsRawValue.valueResponse, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(2000)],
      }),
      sourceChannel: new FormControl(interationsRawValue.sourceChannel),
    });
  }

  getInterations(form: InterationsFormGroup): IInterations | NewInterations {
    return form.getRawValue() as IInterations | NewInterations;
  }

  resetForm(form: InterationsFormGroup, interations: InterationsFormGroupInput): void {
    const interationsRawValue = { ...this.getFormDefaults(), ...interations };
    form.reset(
      {
        ...interationsRawValue,
        id: { value: interationsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): InterationsFormDefaults {
    return {
      id: null,
    };
  }
}
