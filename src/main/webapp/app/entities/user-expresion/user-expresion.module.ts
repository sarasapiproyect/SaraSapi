import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserExpresionComponent } from './list/user-expresion.component';
import { UserExpresionDetailComponent } from './detail/user-expresion-detail.component';
import { UserExpresionUpdateComponent } from './update/user-expresion-update.component';
import { UserExpresionDeleteDialogComponent } from './delete/user-expresion-delete-dialog.component';
import { UserExpresionRoutingModule } from './route/user-expresion-routing.module';

@NgModule({
  imports: [SharedModule, UserExpresionRoutingModule],
  declarations: [UserExpresionComponent, UserExpresionDetailComponent, UserExpresionUpdateComponent, UserExpresionDeleteDialogComponent],
})
export class UserExpresionModule {}
