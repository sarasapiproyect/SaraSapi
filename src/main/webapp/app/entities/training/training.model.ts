import dayjs from 'dayjs/esm';
import { SourceChannel } from 'app/entities/enumerations/source-channel.model';

export interface ITraining {
  id: number;
  value?: string | null;
  sourceChannel?: SourceChannel | null;
  creationDate?: dayjs.Dayjs | null;
  ip?: string | null;
  postionX?: number | null;
  postionY?: number | null;
  sourceInfo?: string | null;
}

export type NewTraining = Omit<ITraining, 'id'> & { id: null };
