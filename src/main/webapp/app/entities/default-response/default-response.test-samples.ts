import { Priority } from 'app/entities/enumerations/priority.model';

import { IDefaultResponse, NewDefaultResponse } from './default-response.model';

export const sampleWithRequiredData: IDefaultResponse = {
  id: 83837,
  defaultValueResponse: 'Keyboard Automotive Refined',
};

export const sampleWithPartialData: IDefaultResponse = {
  id: 44168,
  defaultValueResponse: 'Gorgeous Incredible',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  multimediaVoiceUrl: 'Small deposit',
  saraAnimationUrl: 'whiteboard',
};

export const sampleWithFullData: IDefaultResponse = {
  id: 21158,
  defaultValueResponse: 'Keys',
  priority: Priority['LOW'],
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: false,
  multimediaUrl: 'Industrial Specialist frame',
  multimediaVoiceUrl: 'Metrics',
  saraAnimationUrl: 'Maine',
};

export const sampleWithNewData: NewDefaultResponse = {
  defaultValueResponse: 'program enable',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
