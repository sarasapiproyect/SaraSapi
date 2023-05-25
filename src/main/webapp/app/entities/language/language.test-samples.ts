import { ILanguage, NewLanguage } from './language.model';

export const sampleWithRequiredData: ILanguage = {
  id: 6445,
  name: 'Unbranded Granite Facilitator',
  isoValue: 'Argentina Sleek copying',
  description: 'synthesizing transmit',
  iconsrc: 'Soap Business-focused alarm',
};

export const sampleWithPartialData: ILanguage = {
  id: 86110,
  name: 'Operations',
  isoValue: 'Buckinghamshire Hat deliverables',
  description: 'deposit Handmade Crescent',
  iconsrc: 'connecting deposit',
};

export const sampleWithFullData: ILanguage = {
  id: 75200,
  name: 'Metical',
  isoValue: 'deposit',
  description: 'primary Checking',
  iconsrc: 'Marketing olive',
  imgBlogIcon: '../fake-data/blob/hipster.png',
  imgBlogIconContentType: 'unknown',
};

export const sampleWithNewData: NewLanguage = {
  name: 'Web',
  isoValue: 'Island Electronics',
  description: 'program capacitor',
  iconsrc: 'IB',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
