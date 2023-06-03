import { IIntent } from 'app/entities/intent/intent.model';
import { Priority } from 'app/entities/enumerations/priority.model';
import { ResponseType } from 'app/entities/enumerations/response-type.model';
import { MultimediaType } from 'app/entities/enumerations/multimedia-type.model';

export interface IUserResponse {
  id: number;
  valueResponse?: string | null;
  priority?: Priority | null;
  multimedia?: string | null;
  multimediaContentType?: string | null;
  multimediaVoice?: string | null;
  multimediaVoiceContentType?: string | null;
  saraAnimation?: string | null;
  saraAnimationContentType?: string | null;
  isEndConversation?: boolean | null;
  responseType?: ResponseType | null;
  url?: string | null;
  multimediaUrl?: string | null;
  multimediaVoiceUrl?: string | null;
  saraAnimationUrl?: string | null;
  multimediaType?: MultimediaType | null;
  intents?: Pick<IIntent, 'id'>[] | null;
}

export type NewUserResponse = Omit<IUserResponse, 'id'> & { id: null };
