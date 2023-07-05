import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IContacts, NewContacts } from '../contacts.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContacts for edit and NewContactsFormGroupInput for create.
 */
type ContactsFormGroupInput = IContacts | PartialWithRequiredKeyOf<NewContacts>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IContacts | NewContacts> = Omit<T, 'lastDayConnection'> & {
  lastDayConnection?: string | null;
};

type ContactsFormRawValue = FormValueOf<IContacts>;

type NewContactsFormRawValue = FormValueOf<NewContacts>;

type ContactsFormDefaults = Pick<NewContacts, 'id' | 'lastDayConnection'>;

type ContactsFormGroupContent = {
  id: FormControl<ContactsFormRawValue['id'] | NewContacts['id']>;
  phoneNumber: FormControl<ContactsFormRawValue['phoneNumber']>;
  lastDayConnection: FormControl<ContactsFormRawValue['lastDayConnection']>;
  sourceChannel: FormControl<ContactsFormRawValue['sourceChannel']>;
};

export type ContactsFormGroup = FormGroup<ContactsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContactsFormService {
  createContactsFormGroup(contacts: ContactsFormGroupInput = { id: null }): ContactsFormGroup {
    const contactsRawValue = this.convertContactsToContactsRawValue({
      ...this.getFormDefaults(),
      ...contacts,
    });
    return new FormGroup<ContactsFormGroupContent>({
      id: new FormControl(
        { value: contactsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      phoneNumber: new FormControl(contactsRawValue.phoneNumber, {
        validators: [Validators.required, Validators.maxLength(15)],
      }),
      lastDayConnection: new FormControl(contactsRawValue.lastDayConnection),
      sourceChannel: new FormControl(contactsRawValue.sourceChannel),
    });
  }

  getContacts(form: ContactsFormGroup): IContacts | NewContacts {
    return this.convertContactsRawValueToContacts(form.getRawValue() as ContactsFormRawValue | NewContactsFormRawValue);
  }

  resetForm(form: ContactsFormGroup, contacts: ContactsFormGroupInput): void {
    const contactsRawValue = this.convertContactsToContactsRawValue({ ...this.getFormDefaults(), ...contacts });
    form.reset(
      {
        ...contactsRawValue,
        id: { value: contactsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ContactsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastDayConnection: currentTime,
    };
  }

  private convertContactsRawValueToContacts(rawContacts: ContactsFormRawValue | NewContactsFormRawValue): IContacts | NewContacts {
    return {
      ...rawContacts,
      lastDayConnection: dayjs(rawContacts.lastDayConnection, DATE_TIME_FORMAT),
    };
  }

  private convertContactsToContactsRawValue(
    contacts: IContacts | (Partial<NewContacts> & ContactsFormDefaults)
  ): ContactsFormRawValue | PartialWithRequiredKeyOf<NewContactsFormRawValue> {
    return {
      ...contacts,
      lastDayConnection: contacts.lastDayConnection ? contacts.lastDayConnection.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
