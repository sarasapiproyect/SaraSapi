import { Priority } from 'app/entities/enumerations/priority.model';
import { ResponseType } from 'app/entities/enumerations/response-type.model';

import { IUserResponse, NewUserResponse } from './user-response.model';

export const sampleWithRequiredData: IUserResponse = {
  id: 3833,
  valueResponse: 'indigo Versatile',
};

export const sampleWithPartialData: IUserResponse = {
  id: 52432,
  valueResponse: 'next-generation Bedfordshire',
  priority: Priority['MEDIA'],
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: true,
  responseType: ResponseType['QUERY'],
};

export const sampleWithFullData: IUserResponse = {
  id: 18611,
  valueResponse: 'Lek',
  priority: Priority['LOW'],
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: true,
  responseType: ResponseType['SERVICIO'],
  url: 'http://tania.com',
};

export const sampleWithNewData: NewUserResponse = {
  valueResponse: 'Gloves',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
