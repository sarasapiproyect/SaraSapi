import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ChatbootStyleFormService } from './chatboot-style-form.service';
import { ChatbootStyleService } from '../service/chatboot-style.service';
import { IChatbootStyle } from '../chatboot-style.model';

import { ChatbootStyleUpdateComponent } from './chatboot-style-update.component';

describe('ChatbootStyle Management Update Component', () => {
  let comp: ChatbootStyleUpdateComponent;
  let fixture: ComponentFixture<ChatbootStyleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let chatbootStyleFormService: ChatbootStyleFormService;
  let chatbootStyleService: ChatbootStyleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ChatbootStyleUpdateComponent],
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
      .overrideTemplate(ChatbootStyleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChatbootStyleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    chatbootStyleFormService = TestBed.inject(ChatbootStyleFormService);
    chatbootStyleService = TestBed.inject(ChatbootStyleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const chatbootStyle: IChatbootStyle = { id: 456 };

      activatedRoute.data = of({ chatbootStyle });
      comp.ngOnInit();

      expect(comp.chatbootStyle).toEqual(chatbootStyle);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChatbootStyle>>();
      const chatbootStyle = { id: 123 };
      jest.spyOn(chatbootStyleFormService, 'getChatbootStyle').mockReturnValue(chatbootStyle);
      jest.spyOn(chatbootStyleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chatbootStyle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chatbootStyle }));
      saveSubject.complete();

      // THEN
      expect(chatbootStyleFormService.getChatbootStyle).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(chatbootStyleService.update).toHaveBeenCalledWith(expect.objectContaining(chatbootStyle));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChatbootStyle>>();
      const chatbootStyle = { id: 123 };
      jest.spyOn(chatbootStyleFormService, 'getChatbootStyle').mockReturnValue({ id: null });
      jest.spyOn(chatbootStyleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chatbootStyle: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chatbootStyle }));
      saveSubject.complete();

      // THEN
      expect(chatbootStyleFormService.getChatbootStyle).toHaveBeenCalled();
      expect(chatbootStyleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChatbootStyle>>();
      const chatbootStyle = { id: 123 };
      jest.spyOn(chatbootStyleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chatbootStyle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(chatbootStyleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
