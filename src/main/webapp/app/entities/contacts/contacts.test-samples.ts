import dayjs from 'dayjs/esm';

import { SourceChannel } from 'app/entities/enumerations/source-channel.model';

import { IContacts, NewContacts } from './contacts.model';

export const sampleWithRequiredData: IContacts = {
  id: 22084,
  phoneNumber: 'Zimbabwe',
};

export const sampleWithPartialData: IContacts = {
  id: 88054,
  phoneNumber: 'bypass',
  sourceChannel: SourceChannel['TELEGRAM'],
};

export const sampleWithFullData: IContacts = {
  id: 92144,
  phoneNumber: 'e-business Fori',
  lastDayConnection: dayjs('2023-07-04T03:59'),
  sourceChannel: SourceChannel['TELEGRAM'],
};

export const sampleWithNewData: NewContacts = {
  phoneNumber: 'Account Balboa ',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
