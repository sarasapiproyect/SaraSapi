import { IIntent } from 'app/entities/intent/intent.model';
import { Priority } from 'app/entities/enumerations/priority.model';

export interface IUserExpresion {
  id: number;
  value?: string | null;
  priority?: Priority | null;
  intents?: Pick<IIntent, 'id'>[] | null;
}

export type NewUserExpresion = Omit<IUserExpresion, 'id'> & { id: null };
