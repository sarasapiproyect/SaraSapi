import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserExpresionFormService } from './user-expresion-form.service';
import { UserExpresionService } from '../service/user-expresion.service';
import { IUserExpresion } from '../user-expresion.model';

import { UserExpresionUpdateComponent } from './user-expresion-update.component';

describe('UserExpresion Management Update Component', () => {
  let comp: UserExpresionUpdateComponent;
  let fixture: ComponentFixture<UserExpresionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userExpresionFormService: UserExpresionFormService;
  let userExpresionService: UserExpresionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserExpresionUpdateComponent],
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
      .overrideTemplate(UserExpresionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserExpresionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userExpresionFormService = TestBed.inject(UserExpresionFormService);
    userExpresionService = TestBed.inject(UserExpresionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const userExpresion: IUserExpresion = { id: 456 };

      activatedRoute.data = of({ userExpresion });
      comp.ngOnInit();

      expect(comp.userExpresion).toEqual(userExpresion);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserExpresion>>();
      const userExpresion = { id: 123 };
      jest.spyOn(userExpresionFormService, 'getUserExpresion').mockReturnValue(userExpresion);
      jest.spyOn(userExpresionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userExpresion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userExpresion }));
      saveSubject.complete();

      // THEN
      expect(userExpresionFormService.getUserExpresion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(userExpresionService.update).toHaveBeenCalledWith(expect.objectContaining(userExpresion));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserExpresion>>();
      const userExpresion = { id: 123 };
      jest.spyOn(userExpresionFormService, 'getUserExpresion').mockReturnValue({ id: null });
      jest.spyOn(userExpresionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userExpresion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userExpresion }));
      saveSubject.complete();

      // THEN
      expect(userExpresionFormService.getUserExpresion).toHaveBeenCalled();
      expect(userExpresionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserExpresion>>();
      const userExpresion = { id: 123 };
      jest.spyOn(userExpresionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userExpresion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userExpresionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
