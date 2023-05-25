import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChatbootStyle } from '../chatboot-style.model';
import { ChatbootStyleService } from '../service/chatboot-style.service';

@Injectable({ providedIn: 'root' })
export class ChatbootStyleRoutingResolveService implements Resolve<IChatbootStyle | null> {
  constructor(protected service: ChatbootStyleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChatbootStyle | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((chatbootStyle: HttpResponse<IChatbootStyle>) => {
          if (chatbootStyle.body) {
            return of(chatbootStyle.body);
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
