export interface ILanguage {
  id: number;
  name?: string | null;
  isoValue?: string | null;
  description?: string | null;
  iconsrc?: string | null;
  imgBlogIcon?: string | null;
  imgBlogIconContentType?: string | null;
}

export type NewLanguage = Omit<ILanguage, 'id'> & { id: null };
