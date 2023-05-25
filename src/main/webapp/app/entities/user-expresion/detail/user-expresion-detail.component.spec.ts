import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserExpresionDetailComponent } from './user-expresion-detail.component';

describe('UserExpresion Management Detail Component', () => {
  let comp: UserExpresionDetailComponent;
  let fixture: ComponentFixture<UserExpresionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserExpresionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userExpresion: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserExpresionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserExpresionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userExpresion on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userExpresion).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
