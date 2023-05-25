import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IntentFormService, IntentFormGroup } from './intent-form.service';
import { IIntent } from '../intent.model';
import { IntentService } from '../service/intent.service';
import { ILanguage } from 'app/entities/language/language.model';
import { LanguageService } from 'app/entities/language/service/language.service';
import { IUserExpresion } from 'app/entities/user-expresion/user-expresion.model';
import { UserExpresionService } from 'app/entities/user-expresion/service/user-expresion.service';
import { IUserResponse } from 'app/entities/user-response/user-response.model';
import { UserResponseService } from 'app/entities/user-response/service/user-response.service';
import { IntentType } from 'app/entities/enumerations/intent-type.model';

@Component({
  selector: 'jhi-intent-update',
  templateUrl: './intent-update.component.html',
})
export class IntentUpdateComponent implements OnInit {
  isSaving = false;
  intent: IIntent | null = null;
  intentTypeValues = Object.keys(IntentType);

  languagesSharedCollection: ILanguage[] = [];
  userExpresionsSharedCollection: IUserExpresion[] = [];
  userResponsesSharedCollection: IUserResponse[] = [];

  editForm: IntentFormGroup = this.intentFormService.createIntentFormGroup();

  constructor(
    protected intentService: IntentService,
    protected intentFormService: IntentFormService,
    protected languageService: LanguageService,
    protected userExpresionService: UserExpresionService,
    protected userResponseService: UserResponseService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLanguage = (o1: ILanguage | null, o2: ILanguage | null): boolean => this.languageService.compareLanguage(o1, o2);

  compareUserExpresion = (o1: IUserExpresion | null, o2: IUserExpresion | null): boolean =>
    this.userExpresionService.compareUserExpresion(o1, o2);

  compareUserResponse = (o1: IUserResponse | null, o2: IUserResponse | null): boolean =>
    this.userResponseService.compareUserResponse(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ intent }) => {
      this.intent = intent;
      if (intent) {
        this.updateForm(intent);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const intent = this.intentFormService.getIntent(this.editForm);
    if (intent.id !== null) {
      this.subscribeToSaveResponse(this.intentService.update(intent));
    } else {
      this.subscribeToSaveResponse(this.intentService.create(intent));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIntent>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(intent: IIntent): void {
    this.intent = intent;
    this.intentFormService.resetForm(this.editForm, intent);

    this.languagesSharedCollection = this.languageService.addLanguageToCollectionIfMissing<ILanguage>(
      this.languagesSharedCollection,
      intent.languaje
    );
    this.userExpresionsSharedCollection = this.userExpresionService.addUserExpresionToCollectionIfMissing<IUserExpresion>(
      this.userExpresionsSharedCollection,
      ...(intent.userExpresions ?? [])
    );
    this.userResponsesSharedCollection = this.userResponseService.addUserResponseToCollectionIfMissing<IUserResponse>(
      this.userResponsesSharedCollection,
      ...(intent.userResponses ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.languageService
      .query()
      .pipe(map((res: HttpResponse<ILanguage[]>) => res.body ?? []))
      .pipe(
        map((languages: ILanguage[]) => this.languageService.addLanguageToCollectionIfMissing<ILanguage>(languages, this.intent?.languaje))
      )
      .subscribe((languages: ILanguage[]) => (this.languagesSharedCollection = languages));

    this.userExpresionService
      .query()
      .pipe(map((res: HttpResponse<IUserExpresion[]>) => res.body ?? []))
      .pipe(
        map((userExpresions: IUserExpresion[]) =>
          this.userExpresionService.addUserExpresionToCollectionIfMissing<IUserExpresion>(
            userExpresions,
            ...(this.intent?.userExpresions ?? [])
          )
        )
      )
      .subscribe((userExpresions: IUserExpresion[]) => (this.userExpresionsSharedCollection = userExpresions));

    this.userResponseService
      .query()
      .pipe(map((res: HttpResponse<IUserResponse[]>) => res.body ?? []))
      .pipe(
        map((userResponses: IUserResponse[]) =>
          this.userResponseService.addUserResponseToCollectionIfMissing<IUserResponse>(userResponses, ...(this.intent?.userResponses ?? []))
        )
      )
      .subscribe((userResponses: IUserResponse[]) => (this.userResponsesSharedCollection = userResponses));
  }
}
