import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserExpresion } from '../user-expresion.model';
import { UserExpresionService } from '../service/user-expresion.service';

@Injectable({ providedIn: 'root' })
export class UserExpresionRoutingResolveService implements Resolve<IUserExpresion | null> {
  constructor(protected service: UserExpresionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserExpresion | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userExpresion: HttpResponse<IUserExpresion>) => {
          if (userExpresion.body) {
            return of(userExpresion.body);
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
