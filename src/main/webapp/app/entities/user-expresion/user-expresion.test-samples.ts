import { Priority } from 'app/entities/enumerations/priority.model';

import { IUserExpresion, NewUserExpresion } from './user-expresion.model';

export const sampleWithRequiredData: IUserExpresion = {
  id: 48950,
  value: 'JBOD Future',
};

export const sampleWithPartialData: IUserExpresion = {
  id: 52824,
  value: 'Team-oriented',
  priority: Priority['HIGHT'],
};

export const sampleWithFullData: IUserExpresion = {
  id: 1732,
  value: 'Manat haptic Home',
  priority: Priority['MEDIA'],
};

export const sampleWithNewData: NewUserExpresion = {
  value: 'transmit Gardens',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
