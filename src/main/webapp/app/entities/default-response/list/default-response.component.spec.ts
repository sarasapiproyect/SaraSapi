import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DefaultResponseService } from '../service/default-response.service';

import { DefaultResponseComponent } from './default-response.component';

describe('DefaultResponse Management Component', () => {
  let comp: DefaultResponseComponent;
  let fixture: ComponentFixture<DefaultResponseComponent>;
  let service: DefaultResponseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'default-response', component: DefaultResponseComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [DefaultResponseComponent],
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
      .overrideTemplate(DefaultResponseComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DefaultResponseComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DefaultResponseService);

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
    expect(comp.defaultResponses?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to defaultResponseService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getDefaultResponseIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getDefaultResponseIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
