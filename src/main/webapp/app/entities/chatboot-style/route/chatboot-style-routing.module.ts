import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ChatbootStyleComponent } from '../list/chatboot-style.component';
import { ChatbootStyleDetailComponent } from '../detail/chatboot-style-detail.component';
import { ChatbootStyleUpdateComponent } from '../update/chatboot-style-update.component';
import { ChatbootStyleRoutingResolveService } from './chatboot-style-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const chatbootStyleRoute: Routes = [
  {
    path: '',
    component: ChatbootStyleComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChatbootStyleDetailComponent,
    resolve: {
      chatbootStyle: ChatbootStyleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChatbootStyleUpdateComponent,
    resolve: {
      chatbootStyle: ChatbootStyleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChatbootStyleUpdateComponent,
    resolve: {
      chatbootStyle: ChatbootStyleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(chatbootStyleRoute)],
  exports: [RouterModule],
})
export class ChatbootStyleRoutingModule {}
