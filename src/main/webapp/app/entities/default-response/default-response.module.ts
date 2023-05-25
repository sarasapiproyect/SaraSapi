import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DefaultResponseComponent } from './list/default-response.component';
import { DefaultResponseDetailComponent } from './detail/default-response-detail.component';
import { DefaultResponseUpdateComponent } from './update/default-response-update.component';
import { DefaultResponseDeleteDialogComponent } from './delete/default-response-delete-dialog.component';
import { DefaultResponseRoutingModule } from './route/default-response-routing.module';

@NgModule({
  imports: [SharedModule, DefaultResponseRoutingModule],
  declarations: [
    DefaultResponseComponent,
    DefaultResponseDetailComponent,
    DefaultResponseUpdateComponent,
    DefaultResponseDeleteDialogComponent,
  ],
})
export class DefaultResponseModule {}
