import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDefaultResponse } from '../default-response.model';
import { DefaultResponseService } from '../service/default-response.service';

@Injectable({ providedIn: 'root' })
export class DefaultResponseRoutingResolveService implements Resolve<IDefaultResponse | null> {
  constructor(protected service: DefaultResponseService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDefaultResponse | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((defaultResponse: HttpResponse<IDefaultResponse>) => {
          if (defaultResponse.body) {
            return of(defaultResponse.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
