import { Priority } from 'app/entities/enumerations/priority.model';
import { ResponseType } from 'app/entities/enumerations/response-type.model';
import { MultimediaType } from 'app/entities/enumerations/multimedia-type.model';

import { IUserResponse, NewUserResponse } from './user-response.model';

export const sampleWithRequiredData: IUserResponse = {
  id: 3833,
  valueResponse: 'indigo Versatile',
  responseType: ResponseType['SERVICIO'],
  multimediaType: MultimediaType['AUDIO'],
};

export const sampleWithPartialData: IUserResponse = {
  id: 48365,
  valueResponse: 'calculate',
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  responseType: ResponseType['QUERY'],
  url: 'http://bennett.net',
  saraAnimationUrl: 'protocol',
  multimediaType: MultimediaType['VISUAL'],
};

export const sampleWithFullData: IUserResponse = {
  id: 17876,
  valueResponse: 'Ergonomic intelligence Bacon',
  priority: Priority['LOW'],
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: true,
  responseType: ResponseType['SERVICIO'],
  url: 'https://brandon.net',
  multimediaUrl: 'Cyprus',
  multimediaVoiceUrl: 'Steel Cambridgeshire',
  saraAnimationUrl: 'demand-driven Open-architected customized',
  multimediaType: MultimediaType['VISUAL'],
  showMultimedia: false,
};

export const sampleWithNewData: NewUserResponse = {
  valueResponse: 'cross-platform SCSI Ball',
  responseType: ResponseType['SERVICIO'],
  multimediaType: MultimediaType['AUDIO'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
