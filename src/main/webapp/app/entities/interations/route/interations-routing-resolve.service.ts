import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInterations } from '../interations.model';
import { InterationsService } from '../service/interations.service';

@Injectable({ providedIn: 'root' })
export class InterationsRoutingResolveService implements Resolve<IInterations | null> {
  constructor(protected service: InterationsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInterations | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((interations: HttpResponse<IInterations>) => {
          if (interations.body) {
            return of(interations.body);
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
