import { Priority } from 'app/entities/enumerations/priority.model';
import { MultimediaType } from 'app/entities/enumerations/multimedia-type.model';

export interface IDefaultResponse {
  id: number;
  defaultValueResponse?: string | null;
  priority?: Priority | null;
  multimedia?: string | null;
  multimediaContentType?: string | null;
  multimediaVoice?: string | null;
  multimediaVoiceContentType?: string | null;
  saraAnimation?: string | null;
  saraAnimationContentType?: string | null;
  isEndConversation?: boolean | null;
  multimediaUrl?: string | null;
  multimediaVoiceUrl?: string | null;
  saraAnimationUrl?: string | null;
  multimediaType?: MultimediaType | null;
}

export type NewDefaultResponse = Omit<IDefaultResponse, 'id'> & { id: null };
