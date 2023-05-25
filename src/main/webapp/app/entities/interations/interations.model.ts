import { SourceChannel } from 'app/entities/enumerations/source-channel.model';

export interface IInterations {
  id: number;
  valueRequest?: string | null;
  sourceInfo?: string | null;
  valueResponse?: string | null;
  sourceChannel?: SourceChannel | null;
}

export type NewInterations = Omit<IInterations, 'id'> & { id: null };
