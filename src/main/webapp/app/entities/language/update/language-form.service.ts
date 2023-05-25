import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILanguage, NewLanguage } from '../language.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILanguage for edit and NewLanguageFormGroupInput for create.
 */
type LanguageFormGroupInput = ILanguage | PartialWithRequiredKeyOf<NewLanguage>;

type LanguageFormDefaults = Pick<NewLanguage, 'id'>;

type LanguageFormGroupContent = {
  id: FormControl<ILanguage['id'] | NewLanguage['id']>;
  name: FormControl<ILanguage['name']>;
  isoValue: FormControl<ILanguage['isoValue']>;
  description: FormControl<ILanguage['description']>;
  iconsrc: FormControl<ILanguage['iconsrc']>;
  imgBlogIcon: FormControl<ILanguage['imgBlogIcon']>;
  imgBlogIconContentType: FormControl<ILanguage['imgBlogIconContentType']>;
};

export type LanguageFormGroup = FormGroup<LanguageFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LanguageFormService {
  createLanguageFormGroup(language: LanguageFormGroupInput = { id: null }): LanguageFormGroup {
    const languageRawValue = {
      ...this.getFormDefaults(),
      ...language,
    };
    return new FormGroup<LanguageFormGroupContent>({
      id: new FormControl(
        { value: languageRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(languageRawValue.name, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      isoValue: new FormControl(languageRawValue.isoValue, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      description: new FormControl(languageRawValue.description, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      iconsrc: new FormControl(languageRawValue.iconsrc, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(200)],
      }),
      imgBlogIcon: new FormControl(languageRawValue.imgBlogIcon),
      imgBlogIconContentType: new FormControl(languageRawValue.imgBlogIconContentType),
    });
  }

  getLanguage(form: LanguageFormGroup): ILanguage | NewLanguage {
    return form.getRawValue() as ILanguage | NewLanguage;
  }

  resetForm(form: LanguageFormGroup, language: LanguageFormGroupInput): void {
    const languageRawValue = { ...this.getFormDefaults(), ...language };
    form.reset(
      {
        ...languageRawValue,
        id: { value: languageRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LanguageFormDefaults {
    return {
      id: null,
    };
  }
}
