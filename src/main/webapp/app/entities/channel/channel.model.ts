export interface IChannel {
  id: number;
  description?: string | null;
}

export type NewChannel = Omit<IChannel, 'id'> & { id: null };
