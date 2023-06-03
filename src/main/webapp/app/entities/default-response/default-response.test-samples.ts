import { Priority } from 'app/entities/enumerations/priority.model';
import { MultimediaType } from 'app/entities/enumerations/multimedia-type.model';

import { IDefaultResponse, NewDefaultResponse } from './default-response.model';

export const sampleWithRequiredData: IDefaultResponse = {
  id: 83837,
  defaultValueResponse: 'Keyboard Automotive Refined',
};

export const sampleWithPartialData: IDefaultResponse = {
  id: 37570,
  defaultValueResponse: 'Quality-focused',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  multimediaVoiceUrl: 'Future-proofed',
  saraAnimationUrl: 'Functionality',
};

export const sampleWithFullData: IDefaultResponse = {
  id: 63684,
  defaultValueResponse: 'Arkansas',
  priority: Priority['HIGHT'],
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: true,
  multimediaUrl: 'Multi-layered',
  multimediaVoiceUrl: 'Chair',
  saraAnimationUrl: 'Specialist frame Unbranded',
  multimediaType: MultimediaType['VISUAL'],
};

export const sampleWithNewData: NewDefaultResponse = {
  defaultValueResponse: 'Operations SSL',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
