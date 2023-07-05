import dayjs from 'dayjs/esm';
import { SourceChannel } from 'app/entities/enumerations/source-channel.model';

export interface IContacts {
  id: number;
  phoneNumber?: string | null;
  lastDayConnection?: dayjs.Dayjs | null;
  sourceChannel?: SourceChannel | null;
}

export type NewContacts = Omit<IContacts, 'id'> & { id: null };
