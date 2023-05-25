import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { UserResponseService } from '../service/user-response.service';

import { UserResponseComponent } from './user-response.component';

describe('UserResponse Management Component', () => {
  let comp: UserResponseComponent;
  let fixture: ComponentFixture<UserResponseComponent>;
  let service: UserResponseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'user-response', component: UserResponseComponent }]), HttpClientTestingModule],
      declarations: [UserResponseComponent],
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
      .overrideTemplate(UserResponseComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserResponseComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UserResponseService);

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
    expect(comp.userResponses?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to userResponseService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getUserResponseIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getUserResponseIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
