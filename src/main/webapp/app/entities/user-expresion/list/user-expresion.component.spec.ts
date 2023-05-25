import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { UserExpresionService } from '../service/user-expresion.service';

import { UserExpresionComponent } from './user-expresion.component';

describe('UserExpresion Management Component', () => {
  let comp: UserExpresionComponent;
  let fixture: ComponentFixture<UserExpresionComponent>;
  let service: UserExpresionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'user-expresion', component: UserExpresionComponent }]), HttpClientTestingModule],
      declarations: [UserExpresionComponent],
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
      .overrideTemplate(UserExpresionComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserExpresionComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UserExpresionService);

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
    expect(comp.userExpresions?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to userExpresionService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getUserExpresionIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getUserExpresionIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
