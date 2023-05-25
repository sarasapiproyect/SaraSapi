import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InterationsDetailComponent } from './interations-detail.component';

describe('Interations Management Detail Component', () => {
  let comp: InterationsDetailComponent;
  let fixture: ComponentFixture<InterationsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InterationsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ interations: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(InterationsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InterationsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load interations on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.interations).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
