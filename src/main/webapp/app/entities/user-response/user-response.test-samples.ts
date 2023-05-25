import { Priority } from 'app/entities/enumerations/priority.model';

import { IUserResponse, NewUserResponse } from './user-response.model';

export const sampleWithRequiredData: IUserResponse = {
  id: 3833,
  valueResponse: 'indigo Versatile',
};

export const sampleWithPartialData: IUserResponse = {
  id: 78915,
  valueResponse: 'Grenada hacking',
  priority: Priority['LOW'],
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: true,
};

export const sampleWithFullData: IUserResponse = {
  id: 64957,
  valueResponse: 'Concrete Lek',
  priority: Priority['LOW'],
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: true,
};

export const sampleWithNewData: NewUserResponse = {
  valueResponse: 'Salad Plastic Bacon',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
