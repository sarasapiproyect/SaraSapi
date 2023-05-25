import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserResponseComponent } from './list/user-response.component';
import { UserResponseDetailComponent } from './detail/user-response-detail.component';
import { UserResponseUpdateComponent } from './update/user-response-update.component';
import { UserResponseDeleteDialogComponent } from './delete/user-response-delete-dialog.component';
import { UserResponseRoutingModule } from './route/user-response-routing.module';

@NgModule({
  imports: [SharedModule, UserResponseRoutingModule],
  declarations: [UserResponseComponent, UserResponseDetailComponent, UserResponseUpdateComponent, UserResponseDeleteDialogComponent],
})
export class UserResponseModule {}
