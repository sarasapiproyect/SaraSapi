export interface IChatbootStyle {
  id: number;
  nameProperties?: string | null;
  value?: string | null;
  multimedia?: string | null;
  multimediaContentType?: string | null;
}

export type NewChatbootStyle = Omit<IChatbootStyle, 'id'> & { id: null };
