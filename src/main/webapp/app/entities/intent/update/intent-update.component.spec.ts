import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IntentFormService } from './intent-form.service';
import { IntentService } from '../service/intent.service';
import { IIntent } from '../intent.model';
import { ILanguage } from 'app/entities/language/language.model';
import { LanguageService } from 'app/entities/language/service/language.service';
import { IUserExpresion } from 'app/entities/user-expresion/user-expresion.model';
import { UserExpresionService } from 'app/entities/user-expresion/service/user-expresion.service';
import { IUserResponse } from 'app/entities/user-response/user-response.model';
import { UserResponseService } from 'app/entities/user-response/service/user-response.service';

import { IntentUpdateComponent } from './intent-update.component';

describe('Intent Management Update Component', () => {
  let comp: IntentUpdateComponent;
  let fixture: ComponentFixture<IntentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let intentFormService: IntentFormService;
  let intentService: IntentService;
  let languageService: LanguageService;
  let userExpresionService: UserExpresionService;
  let userResponseService: UserResponseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IntentUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(IntentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IntentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    intentFormService = TestBed.inject(IntentFormService);
    intentService = TestBed.inject(IntentService);
    languageService = TestBed.inject(LanguageService);
    userExpresionService = TestBed.inject(UserExpresionService);
    userResponseService = TestBed.inject(UserResponseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Language query and add missing value', () => {
      const intent: IIntent = { id: 456 };
      const languaje: ILanguage = { id: 32582 };
      intent.languaje = languaje;

      const languageCollection: ILanguage[] = [{ id: 38821 }];
      jest.spyOn(languageService, 'query').mockReturnValue(of(new HttpResponse({ body: languageCollection })));
      const additionalLanguages = [languaje];
      const expectedCollection: ILanguage[] = [...additionalLanguages, ...languageCollection];
      jest.spyOn(languageService, 'addLanguageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ intent });
      comp.ngOnInit();

      expect(languageService.query).toHaveBeenCalled();
      expect(languageService.addLanguageToCollectionIfMissing).toHaveBeenCalledWith(
        languageCollection,
        ...additionalLanguages.map(expect.objectContaining)
      );
      expect(comp.languagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UserExpresion query and add missing value', () => {
      const intent: IIntent = { id: 456 };
      const userExpresions: IUserExpresion[] = [{ id: 31419 }];
      intent.userExpresions = userExpresions;

      const userExpresionCollection: IUserExpresion[] = [{ id: 57442 }];
      jest.spyOn(userExpresionService, 'query').mockReturnValue(of(new HttpResponse({ body: userExpresionCollection })));
      const additionalUserExpresions = [...userExpresions];
      const expectedCollection: IUserExpresion[] = [...additionalUserExpresions, ...userExpresionCollection];
      jest.spyOn(userExpresionService, 'addUserExpresionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ intent });
      comp.ngOnInit();

      expect(userExpresionService.query).toHaveBeenCalled();
      expect(userExpresionService.addUserExpresionToCollectionIfMissing).toHaveBeenCalledWith(
        userExpresionCollection,
        ...additionalUserExpresions.map(expect.objectContaining)
      );
      expect(comp.userExpresionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UserResponse query and add missing value', () => {
      const intent: IIntent = { id: 456 };
      const userResponses: IUserResponse[] = [{ id: 76141 }];
      intent.userResponses = userResponses;

      const userResponseCollection: IUserResponse[] = [{ id: 51408 }];
      jest.spyOn(userResponseService, 'query').mockReturnValue(of(new HttpResponse({ body: userResponseCollection })));
      const additionalUserResponses = [...userResponses];
      const expectedCollection: IUserResponse[] = [...additionalUserResponses, ...userResponseCollection];
      jest.spyOn(userResponseService, 'addUserResponseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ intent });
      comp.ngOnInit();

      expect(userResponseService.query).toHaveBeenCalled();
      expect(userResponseService.addUserResponseToCollectionIfMissing).toHaveBeenCalledWith(
        userResponseCollection,
        ...additionalUserResponses.map(expect.objectContaining)
      );
      expect(comp.userResponsesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const intent: IIntent = { id: 456 };
      const languaje: ILanguage = { id: 37747 };
      intent.languaje = languaje;
      const userExpresion: IUserExpresion = { id: 60281 };
      intent.userExpresions = [userExpresion];
      const userResponse: IUserResponse = { id: 83910 };
      intent.userResponses = [userResponse];

      activatedRoute.data = of({ intent });
      comp.ngOnInit();

      expect(comp.languagesSharedCollection).toContain(languaje);
      expect(comp.userExpresionsSharedCollection).toContain(userExpresion);
      expect(comp.userResponsesSharedCollection).toContain(userResponse);
      expect(comp.intent).toEqual(intent);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIntent>>();
      const intent = { id: 123 };
      jest.spyOn(intentFormService, 'getIntent').mockReturnValue(intent);
      jest.spyOn(intentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ intent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: intent }));
      saveSubject.complete();

      // THEN
      expect(intentFormService.getIntent).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(intentService.update).toHaveBeenCalledWith(expect.objectContaining(intent));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIntent>>();
      const intent = { id: 123 };
      jest.spyOn(intentFormService, 'getIntent').mockReturnValue({ id: null });
      jest.spyOn(intentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ intent: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: intent }));
      saveSubject.complete();

      // THEN
      expect(intentFormService.getIntent).toHaveBeenCalled();
      expect(intentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIntent>>();
      const intent = { id: 123 };
      jest.spyOn(intentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ intent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(intentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLanguage', () => {
      it('Should forward to languageService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(languageService, 'compareLanguage');
        comp.compareLanguage(entity, entity2);
        expect(languageService.compareLanguage).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUserExpresion', () => {
      it('Should forward to userExpresionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userExpresionService, 'compareUserExpresion');
        comp.compareUserExpresion(entity, entity2);
        expect(userExpresionService.compareUserExpresion).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUserResponse', () => {
      it('Should forward to userResponseService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userResponseService, 'compareUserResponse');
        comp.compareUserResponse(entity, entity2);
        expect(userResponseService.compareUserResponse).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
