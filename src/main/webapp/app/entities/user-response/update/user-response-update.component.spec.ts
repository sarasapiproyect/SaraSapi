import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserResponseFormService } from './user-response-form.service';
import { UserResponseService } from '../service/user-response.service';
import { IUserResponse } from '../user-response.model';

import { UserResponseUpdateComponent } from './user-response-update.component';

describe('UserResponse Management Update Component', () => {
  let comp: UserResponseUpdateComponent;
  let fixture: ComponentFixture<UserResponseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userResponseFormService: UserResponseFormService;
  let userResponseService: UserResponseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserResponseUpdateComponent],
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
      .overrideTemplate(UserResponseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserResponseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userResponseFormService = TestBed.inject(UserResponseFormService);
    userResponseService = TestBed.inject(UserResponseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const userResponse: IUserResponse = { id: 456 };

      activatedRoute.data = of({ userResponse });
      comp.ngOnInit();

      expect(comp.userResponse).toEqual(userResponse);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserResponse>>();
      const userResponse = { id: 123 };
      jest.spyOn(userResponseFormService, 'getUserResponse').mockReturnValue(userResponse);
      jest.spyOn(userResponseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userResponse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userResponse }));
      saveSubject.complete();

      // THEN
      expect(userResponseFormService.getUserResponse).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(userResponseService.update).toHaveBeenCalledWith(expect.objectContaining(userResponse));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserResponse>>();
      const userResponse = { id: 123 };
      jest.spyOn(userResponseFormService, 'getUserResponse').mockReturnValue({ id: null });
      jest.spyOn(userResponseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userResponse: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userResponse }));
      saveSubject.complete();

      // THEN
      expect(userResponseFormService.getUserResponse).toHaveBeenCalled();
      expect(userResponseService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserResponse>>();
      const userResponse = { id: 123 };
      jest.spyOn(userResponseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userResponse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userResponseService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
