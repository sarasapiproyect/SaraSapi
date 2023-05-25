import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserExpresionComponent } from '../list/user-expresion.component';
import { UserExpresionDetailComponent } from '../detail/user-expresion-detail.component';
import { UserExpresionUpdateComponent } from '../update/user-expresion-update.component';
import { UserExpresionRoutingResolveService } from './user-expresion-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const userExpresionRoute: Routes = [
  {
    path: '',
    component: UserExpresionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserExpresionDetailComponent,
    resolve: {
      userExpresion: UserExpresionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserExpresionUpdateComponent,
    resolve: {
      userExpresion: UserExpresionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserExpresionUpdateComponent,
    resolve: {
      userExpresion: UserExpresionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userExpresionRoute)],
  exports: [RouterModule],
})
export class UserExpresionRoutingModule {}
