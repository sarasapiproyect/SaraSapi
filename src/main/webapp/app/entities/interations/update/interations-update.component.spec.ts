import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InterationsFormService } from './interations-form.service';
import { InterationsService } from '../service/interations.service';
import { IInterations } from '../interations.model';

import { InterationsUpdateComponent } from './interations-update.component';

describe('Interations Management Update Component', () => {
  let comp: InterationsUpdateComponent;
  let fixture: ComponentFixture<InterationsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let interationsFormService: InterationsFormService;
  let interationsService: InterationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InterationsUpdateComponent],
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
      .overrideTemplate(InterationsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InterationsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    interationsFormService = TestBed.inject(InterationsFormService);
    interationsService = TestBed.inject(InterationsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const interations: IInterations = { id: 456 };

      activatedRoute.data = of({ interations });
      comp.ngOnInit();

      expect(comp.interations).toEqual(interations);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterations>>();
      const interations = { id: 123 };
      jest.spyOn(interationsFormService, 'getInterations').mockReturnValue(interations);
      jest.spyOn(interationsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interations });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interations }));
      saveSubject.complete();

      // THEN
      expect(interationsFormService.getInterations).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(interationsService.update).toHaveBeenCalledWith(expect.objectContaining(interations));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterations>>();
      const interations = { id: 123 };
      jest.spyOn(interationsFormService, 'getInterations').mockReturnValue({ id: null });
      jest.spyOn(interationsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interations: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interations }));
      saveSubject.complete();

      // THEN
      expect(interationsFormService.getInterations).toHaveBeenCalled();
      expect(interationsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterations>>();
      const interations = { id: 123 };
      jest.spyOn(interationsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interations });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(interationsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
