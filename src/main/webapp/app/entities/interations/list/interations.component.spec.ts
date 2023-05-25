import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { InterationsService } from '../service/interations.service';

import { InterationsComponent } from './interations.component';

describe('Interations Management Component', () => {
  let comp: InterationsComponent;
  let fixture: ComponentFixture<InterationsComponent>;
  let service: InterationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'interations', component: InterationsComponent }]), HttpClientTestingModule],
      declarations: [InterationsComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(InterationsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InterationsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(InterationsService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.interations?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to interationsService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getInterationsIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getInterationsIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
