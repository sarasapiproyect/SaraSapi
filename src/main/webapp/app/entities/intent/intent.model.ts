import dayjs from 'dayjs/esm';
import { ILanguage } from 'app/entities/language/language.model';
import { IUserExpresion } from 'app/entities/user-expresion/user-expresion.model';
import { IUserResponse } from 'app/entities/user-response/user-response.model';
import { IntentType } from 'app/entities/enumerations/intent-type.model';

export interface IIntent {
  id: number;
  intenType?: IntentType | null;
  name?: string | null;
  description?: string | null;
  urlRequest?: string | null;
  enabled?: boolean | null;
  creationDate?: dayjs.Dayjs | null;
  languaje?: Pick<ILanguage, 'id' | 'name'> | null;
  userExpresions?: Pick<IUserExpresion, 'id' | 'value'>[] | null;
  userResponses?: Pick<IUserResponse, 'id' | 'valueResponse'>[] | null;
}

export type NewIntent = Omit<IIntent, 'id'> & { id: null };
