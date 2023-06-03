import { Priority } from 'app/entities/enumerations/priority.model';
import { ResponseType } from 'app/entities/enumerations/response-type.model';
import { MultimediaType } from 'app/entities/enumerations/multimedia-type.model';

import { IUserResponse, NewUserResponse } from './user-response.model';

export const sampleWithRequiredData: IUserResponse = {
  id: 3833,
  valueResponse: 'indigo Versatile',
};

export const sampleWithPartialData: IUserResponse = {
  id: 12875,
  valueResponse: 'product Avon',
  priority: Priority['LOW'],
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: true,
  responseType: ResponseType['QUERY'],
  multimediaUrl: 'Mouse Ergonomic intelligence',
  multimediaType: MultimediaType['AUDIO'],
};

export const sampleWithFullData: IUserResponse = {
  id: 81619,
  valueResponse: 'multi-tasking',
  priority: Priority['HIGHT'],
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: false,
  responseType: ResponseType['SERVICIO'],
  url: 'http://kacey.biz',
  multimediaUrl: 'Avon Shoes',
  multimediaVoiceUrl: 'Functionality',
  saraAnimationUrl: 'Open-architected',
  multimediaType: MultimediaType['AUDIO'],
};

export const sampleWithNewData: NewUserResponse = {
  valueResponse: 'transmitter Tunisian solution',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
