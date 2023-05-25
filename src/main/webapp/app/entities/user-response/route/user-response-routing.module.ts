import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserResponseComponent } from '../list/user-response.component';
import { UserResponseDetailComponent } from '../detail/user-response-detail.component';
import { UserResponseUpdateComponent } from '../update/user-response-update.component';
import { UserResponseRoutingResolveService } from './user-response-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const userResponseRoute: Routes = [
  {
    path: '',
    component: UserResponseComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserResponseDetailComponent,
    resolve: {
      userResponse: UserResponseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserResponseUpdateComponent,
    resolve: {
      userResponse: UserResponseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserResponseUpdateComponent,
    resolve: {
      userResponse: UserResponseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userResponseRoute)],
  exports: [RouterModule],
})
export class UserResponseRoutingModule {}
