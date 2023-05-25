import dayjs from 'dayjs/esm';

import { SourceChannel } from 'app/entities/enumerations/source-channel.model';

import { ITraining, NewTraining } from './training.model';

export const sampleWithRequiredData: ITraining = {
  id: 91438,
  value: 'withdrawal Salad',
  ip: 'Gorgeous invoice scalable',
  sourceInfo: 'black navigate Philippines',
};

export const sampleWithPartialData: ITraining = {
  id: 28221,
  value: 'Senior',
  sourceChannel: SourceChannel['WHATSAPP'],
  ip: 'Avon',
  postionY: 53211,
  sourceInfo: 'real-time',
};

export const sampleWithFullData: ITraining = {
  id: 43736,
  value: 'Bacon Berkshire Salad',
  sourceChannel: SourceChannel['WEB'],
  creationDate: dayjs('2023-05-20T12:55'),
  ip: 'Paradigm',
  postionX: 29718,
  postionY: 47980,
  sourceInfo: 'Wells Handmade users',
};

export const sampleWithNewData: NewTraining = {
  value: 'Hong Hawaii',
  ip: 'Engineer Pants',
  sourceInfo: 'solid compress Orchestrator',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
