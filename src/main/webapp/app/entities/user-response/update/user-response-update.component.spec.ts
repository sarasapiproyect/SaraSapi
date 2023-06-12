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
import { IChannel } from 'app/entities/channel/channel.model';
import { ChannelService } from 'app/entities/channel/service/channel.service';

import { UserResponseUpdateComponent } from './user-response-update.component';

describe('UserResponse Management Update Component', () => {
  let comp: UserResponseUpdateComponent;
  let fixture: ComponentFixture<UserResponseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userResponseFormService: UserResponseFormService;
  let userResponseService: UserResponseService;
  let channelService: ChannelService;

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
    channelService = TestBed.inject(ChannelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Channel query and add missing value', () => {
      const userResponse: IUserResponse = { id: 456 };
      const channelMultimedias: IChannel[] = [{ id: 9704 }];
      userResponse.channelMultimedias = channelMultimedias;
      const channelVoices: IChannel[] = [{ id: 796 }];
      userResponse.channelVoices = channelVoices;
      const channelAnimations: IChannel[] = [{ id: 71806 }];
      userResponse.channelAnimations = channelAnimations;

      const channelCollection: IChannel[] = [{ id: 30837 }];
      jest.spyOn(channelService, 'query').mockReturnValue(of(new HttpResponse({ body: channelCollection })));
      const additionalChannels = [...channelMultimedias, ...channelVoices, ...channelAnimations];
      const expectedCollection: IChannel[] = [...additionalChannels, ...channelCollection];
      jest.spyOn(channelService, 'addChannelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ userResponse });
      comp.ngOnInit();

      expect(channelService.query).toHaveBeenCalled();
      expect(channelService.addChannelToCollectionIfMissing).toHaveBeenCalledWith(
        channelCollection,
        ...additionalChannels.map(expect.objectContaining)
      );
      expect(comp.channelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const userResponse: IUserResponse = { id: 456 };
      const channelMultimedia: IChannel = { id: 20981 };
      userResponse.channelMultimedias = [channelMultimedia];
      const channelVoice: IChannel = { id: 17237 };
      userResponse.channelVoices = [channelVoice];
      const channelAnimation: IChannel = { id: 44844 };
      userResponse.channelAnimations = [channelAnimation];

      activatedRoute.data = of({ userResponse });
      comp.ngOnInit();

      expect(comp.channelsSharedCollection).toContain(channelMultimedia);
      expect(comp.channelsSharedCollection).toContain(channelVoice);
      expect(comp.channelsSharedCollection).toContain(channelAnimation);
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

  describe('Compare relationships', () => {
    describe('compareChannel', () => {
      it('Should forward to channelService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(channelService, 'compareChannel');
        comp.compareChannel(entity, entity2);
        expect(channelService.compareChannel).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
