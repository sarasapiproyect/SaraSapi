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
  id: 12875,
  valueResponse: 'product Avon',
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  responseType: ResponseType['QUERY'],
  url: 'https://adolfo.net',
  saraAnimationUrl: 'Buckinghamshire',
  multimediaType: MultimediaType['VISUAL'],
};

export const sampleWithFullData: IUserResponse = {
  id: 8217,
  valueResponse: 'Gloves',
  priority: Priority['MEDIA'],
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: false,
  responseType: ResponseType['SERVICIO'],
  url: 'http://earlene.name',
  multimediaUrl: 'panel Concrete Steel',
  multimediaVoiceUrl: 'Dynamic demand-driven',
  saraAnimationUrl: 'Borders',
  multimediaType: MultimediaType['VISUAL'],
};

export const sampleWithNewData: NewUserResponse = {
  valueResponse: 'open-source cross-platform SCSI',
  responseType: ResponseType['QUERY'],
  multimediaType: MultimediaType['AUDIO'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
