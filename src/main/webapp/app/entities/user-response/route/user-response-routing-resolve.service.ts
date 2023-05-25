import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserResponse } from '../user-response.model';
import { UserResponseService } from '../service/user-response.service';

@Injectable({ providedIn: 'root' })
export class UserResponseRoutingResolveService implements Resolve<IUserResponse | null> {
  constructor(protected service: UserResponseService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserResponse | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userResponse: HttpResponse<IUserResponse>) => {
          if (userResponse.body) {
            return of(userResponse.body);
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
