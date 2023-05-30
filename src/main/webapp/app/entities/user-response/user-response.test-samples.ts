import { Priority } from 'app/entities/enumerations/priority.model';
import { ResponseType } from 'app/entities/enumerations/response-type.model';

import { IUserResponse, NewUserResponse } from './user-response.model';

export const sampleWithRequiredData: IUserResponse = {
  id: 3833,
  valueResponse: 'indigo Versatile',
};

export const sampleWithPartialData: IUserResponse = {
  id: 84609,
  valueResponse: 'Bedfordshire',
  priority: Priority['MEDIA'],
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: true,
  responseType: ResponseType['QUERY'],
  multimediaUrl: 'Handcrafted',
};

export const sampleWithFullData: IUserResponse = {
  id: 70976,
  valueResponse: 'Buckinghamshire',
  priority: Priority['HIGHT'],
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: false,
  responseType: ResponseType['QUERY'],
  url: 'http://esta.com',
  multimediaUrl: 'Bacon multi-tasking',
  multimediaVoiceUrl: 'Soft Cyprus Avon',
  saraAnimationUrl: 'migration',
};

export const sampleWithNewData: NewUserResponse = {
  valueResponse: 'lavender bifurcated Borders',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
