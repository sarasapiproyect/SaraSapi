import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ChatbootStyleService } from '../service/chatboot-style.service';

import { ChatbootStyleComponent } from './chatboot-style.component';

describe('ChatbootStyle Management Component', () => {
  let comp: ChatbootStyleComponent;
  let fixture: ComponentFixture<ChatbootStyleComponent>;
  let service: ChatbootStyleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'chatboot-style', component: ChatbootStyleComponent }]), HttpClientTestingModule],
      declarations: [ChatbootStyleComponent],
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
      .overrideTemplate(ChatbootStyleComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChatbootStyleComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ChatbootStyleService);

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
    expect(comp.chatbootStyles?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to chatbootStyleService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getChatbootStyleIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getChatbootStyleIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
