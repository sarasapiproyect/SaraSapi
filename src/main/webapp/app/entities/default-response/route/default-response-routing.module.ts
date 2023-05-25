import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DefaultResponseComponent } from '../list/default-response.component';
import { DefaultResponseDetailComponent } from '../detail/default-response-detail.component';
import { DefaultResponseUpdateComponent } from '../update/default-response-update.component';
import { DefaultResponseRoutingResolveService } from './default-response-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const defaultResponseRoute: Routes = [
  {
    path: '',
    component: DefaultResponseComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DefaultResponseDetailComponent,
    resolve: {
      defaultResponse: DefaultResponseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DefaultResponseUpdateComponent,
    resolve: {
      defaultResponse: DefaultResponseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DefaultResponseUpdateComponent,
    resolve: {
      defaultResponse: DefaultResponseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(defaultResponseRoute)],
  exports: [RouterModule],
})
export class DefaultResponseRoutingModule {}
