import { Priority } from 'app/entities/enumerations/priority.model';
import { MultimediaType } from 'app/entities/enumerations/multimedia-type.model';

import { IDefaultResponse, NewDefaultResponse } from './default-response.model';

export const sampleWithRequiredData: IDefaultResponse = {
  id: 83837,
  defaultValueResponse: 'Keyboard Automotive Refined',
};

export const sampleWithPartialData: IDefaultResponse = {
  id: 8254,
  defaultValueResponse: 'Burundi',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  multimediaVoiceUrl: 'robust',
  saraAnimationUrl: 'Extended Pants overriding',
};

export const sampleWithFullData: IDefaultResponse = {
  id: 47787,
  defaultValueResponse: '6th driver',
  priority: Priority['HIGHT'],
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
  multimediaVoice: '../fake-data/blob/hipster.png',
  multimediaVoiceContentType: 'unknown',
  saraAnimation: '../fake-data/blob/hipster.png',
  saraAnimationContentType: 'unknown',
  isEndConversation: true,
  multimediaUrl: 'Plastic',
  multimediaVoiceUrl: 'Unbranded Maine Buckinghamshire',
  saraAnimationUrl: 'enable',
  multimediaType: MultimediaType['VISUAL'],
  showMultimedia: true,
};

export const sampleWithNewData: NewDefaultResponse = {
  defaultValueResponse: 'Producer target',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
