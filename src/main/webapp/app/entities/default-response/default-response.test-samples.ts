import { Priority } from 'app/entities/enumerations/priority.model';

import { IDefaultResponse, NewDefaultResponse } from './default-response.model';

export const sampleWithRequiredData: IDefaultResponse = {
  id: 83837,
  defaultValueResponse: 'Keyboard Automotive Refined',
};

export const sampleWithPartialData: IDefaultResponse = {
  id: 32370,
  defaultValueResponse: 'system Gorgeous',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
};

export const sampleWithFullData: IDefaultResponse = {
  id: 9116,
  defaultValueResponse: 'Future-proofed',
  priority: Priority['LOW'],
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: true,
};

export const sampleWithNewData: NewDefaultResponse = {
  defaultValueResponse: 'motivating',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
