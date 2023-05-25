import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InterationsComponent } from '../list/interations.component';
import { InterationsDetailComponent } from '../detail/interations-detail.component';
import { InterationsUpdateComponent } from '../update/interations-update.component';
import { InterationsRoutingResolveService } from './interations-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const interationsRoute: Routes = [
  {
    path: '',
    component: InterationsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InterationsDetailComponent,
    resolve: {
      interations: InterationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InterationsUpdateComponent,
    resolve: {
      interations: InterationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InterationsUpdateComponent,
    resolve: {
      interations: InterationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(interationsRoute)],
  exports: [RouterModule],
})
export class InterationsRoutingModule {}
