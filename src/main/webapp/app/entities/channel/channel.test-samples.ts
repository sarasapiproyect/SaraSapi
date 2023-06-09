import { IChannel, NewChannel } from './channel.model';

export const sampleWithRequiredData: IChannel = {
  id: 35356,
  description: 'Account Avon Unbrand',
};

export const sampleWithPartialData: IChannel = {
  id: 59571,
  description: 'Soft',
};

export const sampleWithFullData: IChannel = {
  id: 81927,
  description: 'Mouse iterate Reduce',
};

export const sampleWithNewData: NewChannel = {
  description: 'input Dynamic indexi',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
