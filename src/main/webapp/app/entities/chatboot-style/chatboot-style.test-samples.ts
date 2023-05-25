import { IChatbootStyle, NewChatbootStyle } from './chatboot-style.model';

export const sampleWithRequiredData: IChatbootStyle = {
  id: 39972,
  nameProperties: 'networks Latvia',
  value: 'Incredible Liaison',
};

export const sampleWithPartialData: IChatbootStyle = {
  id: 59130,
  nameProperties: 'Agent Rubber Ball',
  value: 'Guarani',
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
};

export const sampleWithFullData: IChatbootStyle = {
  id: 31333,
  nameProperties: 'Investor Electronics',
  value: 'Branch',
  multimedia: '../fake-data/blob/hipster.png',
  multimediaContentType: 'unknown',
};

export const sampleWithNewData: NewChatbootStyle = {
  nameProperties: 'hacking state',
  value: 'collaboration navigate',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
