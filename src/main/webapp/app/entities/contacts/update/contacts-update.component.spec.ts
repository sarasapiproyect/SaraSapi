import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContactsFormService } from './contacts-form.service';
import { ContactsService } from '../service/contacts.service';
import { IContacts } from '../contacts.model';

import { ContactsUpdateComponent } from './contacts-update.component';

describe('Contacts Management Update Component', () => {
  let comp: ContactsUpdateComponent;
  let fixture: ComponentFixture<ContactsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contactsFormService: ContactsFormService;
  let contactsService: ContactsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ContactsUpdateComponent],
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
      .overrideTemplate(ContactsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContactsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contactsFormService = TestBed.inject(ContactsFormService);
    contactsService = TestBed.inject(ContactsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const contacts: IContacts = { id: 456 };

      activatedRoute.data = of({ contacts });
      comp.ngOnInit();

      expect(comp.contacts).toEqual(contacts);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContacts>>();
      const contacts = { id: 123 };
      jest.spyOn(contactsFormService, 'getContacts').mockReturnValue(contacts);
      jest.spyOn(contactsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contacts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contacts }));
      saveSubject.complete();

      // THEN
      expect(contactsFormService.getContacts).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contactsService.update).toHaveBeenCalledWith(expect.objectContaining(contacts));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContacts>>();
      const contacts = { id: 123 };
      jest.spyOn(contactsFormService, 'getContacts').mockReturnValue({ id: null });
      jest.spyOn(contactsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contacts: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contacts }));
      saveSubject.complete();

      // THEN
      expect(contactsFormService.getContacts).toHaveBeenCalled();
      expect(contactsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContacts>>();
      const contacts = { id: 123 };
      jest.spyOn(contactsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contacts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contactsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
