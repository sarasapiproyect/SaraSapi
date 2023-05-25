import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DefaultResponseFormService } from './default-response-form.service';
import { DefaultResponseService } from '../service/default-response.service';
import { IDefaultResponse } from '../default-response.model';

import { DefaultResponseUpdateComponent } from './default-response-update.component';

describe('DefaultResponse Management Update Component', () => {
  let comp: DefaultResponseUpdateComponent;
  let fixture: ComponentFixture<DefaultResponseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let defaultResponseFormService: DefaultResponseFormService;
  let defaultResponseService: DefaultResponseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DefaultResponseUpdateComponent],
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
      .overrideTemplate(DefaultResponseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DefaultResponseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    defaultResponseFormService = TestBed.inject(DefaultResponseFormService);
    defaultResponseService = TestBed.inject(DefaultResponseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const defaultResponse: IDefaultResponse = { id: 456 };

      activatedRoute.data = of({ defaultResponse });
      comp.ngOnInit();

      expect(comp.defaultResponse).toEqual(defaultResponse);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDefaultResponse>>();
      const defaultResponse = { id: 123 };
      jest.spyOn(defaultResponseFormService, 'getDefaultResponse').mockReturnValue(defaultResponse);
      jest.spyOn(defaultResponseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ defaultResponse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: defaultResponse }));
      saveSubject.complete();

      // THEN
      expect(defaultResponseFormService.getDefaultResponse).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(defaultResponseService.update).toHaveBeenCalledWith(expect.objectContaining(defaultResponse));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDefaultResponse>>();
      const defaultResponse = { id: 123 };
      jest.spyOn(defaultResponseFormService, 'getDefaultResponse').mockReturnValue({ id: null });
      jest.spyOn(defaultResponseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ defaultResponse: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: defaultResponse }));
      saveSubject.complete();

      // THEN
      expect(defaultResponseFormService.getDefaultResponse).toHaveBeenCalled();
      expect(defaultResponseService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDefaultResponse>>();
      const defaultResponse = { id: 123 };
      jest.spyOn(defaultResponseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ defaultResponse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(defaultResponseService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
