import dayjs from 'dayjs/esm';

import { IntentType } from 'app/entities/enumerations/intent-type.model';

import { IIntent, NewIntent } from './intent.model';

export const sampleWithRequiredData: IIntent = {
  id: 80050,
  name: 'synthesize Michigan',
  description: 'parsing',
  urlRequest: 'IB turn-key',
};

export const sampleWithPartialData: IIntent = {
  id: 43854,
  intenType: IntentType['INFO'],
  name: 'ivory',
  description: 'Maine programming',
  urlRequest: 'parsing SQL',
  enabled: true,
  creationDate: dayjs('2023-05-19T22:21'),
};

export const sampleWithFullData: IIntent = {
  id: 62264,
  intenType: IntentType['BACK_END_INFO'],
  name: 'invoice protocol Beauty',
  description: 'haptic',
  urlRequest: 'lavender client-server benchmark',
  enabled: false,
  creationDate: dayjs('2023-05-20T07:43'),
};

export const sampleWithNewData: NewIntent = {
  name: 'Manors Orchestrator Republic',
  description: 'Engineer',
  urlRequest: 'neural Borders Chief',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
