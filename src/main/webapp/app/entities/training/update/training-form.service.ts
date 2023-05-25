import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITraining, NewTraining } from '../training.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITraining for edit and NewTrainingFormGroupInput for create.
 */
type TrainingFormGroupInput = ITraining | PartialWithRequiredKeyOf<NewTraining>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITraining | NewTraining> = Omit<T, 'creationDate'> & {
  creationDate?: string | null;
};

type TrainingFormRawValue = FormValueOf<ITraining>;

type NewTrainingFormRawValue = FormValueOf<NewTraining>;

type TrainingFormDefaults = Pick<NewTraining, 'id' | 'creationDate'>;

type TrainingFormGroupContent = {
  id: FormControl<TrainingFormRawValue['id'] | NewTraining['id']>;
  value: FormControl<TrainingFormRawValue['value']>;
  sourceChannel: FormControl<TrainingFormRawValue['sourceChannel']>;
  creationDate: FormControl<TrainingFormRawValue['creationDate']>;
  ip: FormControl<TrainingFormRawValue['ip']>;
  postionX: FormControl<TrainingFormRawValue['postionX']>;
  postionY: FormControl<TrainingFormRawValue['postionY']>;
  sourceInfo: FormControl<TrainingFormRawValue['sourceInfo']>;
};

export type TrainingFormGroup = FormGroup<TrainingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TrainingFormService {
  createTrainingFormGroup(training: TrainingFormGroupInput = { id: null }): TrainingFormGroup {
    const trainingRawValue = this.convertTrainingToTrainingRawValue({
      ...this.getFormDefaults(),
      ...training,
    });
    return new FormGroup<TrainingFormGroupContent>({
      id: new FormControl(
        { value: trainingRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      value: new FormControl(trainingRawValue.value, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(300)],
      }),
      sourceChannel: new FormControl(trainingRawValue.sourceChannel),
      creationDate: new FormControl(trainingRawValue.creationDate),
      ip: new FormControl(trainingRawValue.ip, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(300)],
      }),
      postionX: new FormControl(trainingRawValue.postionX),
      postionY: new FormControl(trainingRawValue.postionY),
      sourceInfo: new FormControl(trainingRawValue.sourceInfo, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(300)],
      }),
    });
  }

  getTraining(form: TrainingFormGroup): ITraining | NewTraining {
    return this.convertTrainingRawValueToTraining(form.getRawValue() as TrainingFormRawValue | NewTrainingFormRawValue);
  }

  resetForm(form: TrainingFormGroup, training: TrainingFormGroupInput): void {
    const trainingRawValue = this.convertTrainingToTrainingRawValue({ ...this.getFormDefaults(), ...training });
    form.reset(
      {
        ...trainingRawValue,
        id: { value: trainingRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TrainingFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
    };
  }

  private convertTrainingRawValueToTraining(rawTraining: TrainingFormRawValue | NewTrainingFormRawValue): ITraining | NewTraining {
    return {
      ...rawTraining,
      creationDate: dayjs(rawTraining.creationDate, DATE_TIME_FORMAT),
    };
  }

  private convertTrainingToTrainingRawValue(
    training: ITraining | (Partial<NewTraining> & TrainingFormDefaults)
  ): TrainingFormRawValue | PartialWithRequiredKeyOf<NewTrainingFormRawValue> {
    return {
      ...training,
      creationDate: training.creationDate ? training.creationDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
