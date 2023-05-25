import { SourceChannel } from 'app/entities/enumerations/source-channel.model';

import { IInterations, NewInterations } from './interations.model';

export const sampleWithRequiredData: IInterations = {
  id: 35607,
  valueRequest: 'bandwidth Tasty',
  sourceInfo: 'Account upward-trending',
  valueResponse: 'Exclusive Buckinghamshire',
};

export const sampleWithPartialData: IInterations = {
  id: 78541,
  valueRequest: 'input feed',
  sourceInfo: 'definition program Principal',
  valueResponse: 'Pennsylvania Ohio',
  sourceChannel: SourceChannel['WHATSAPP'],
};

export const sampleWithFullData: IInterations = {
  id: 36120,
  valueRequest: 'Mouse time-frame',
  sourceInfo: 'projection',
  valueResponse: 'solid Intelligent',
  sourceChannel: SourceChannel['WEB'],
};

export const sampleWithNewData: NewInterations = {
  valueRequest: 'Reactive',
  sourceInfo: 'payment Legacy Awesome',
  valueResponse: 'Buckinghamshire',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
