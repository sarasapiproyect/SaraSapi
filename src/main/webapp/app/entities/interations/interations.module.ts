import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InterationsComponent } from './list/interations.component';
import { InterationsDetailComponent } from './detail/interations-detail.component';
import { InterationsUpdateComponent } from './update/interations-update.component';
import { InterationsDeleteDialogComponent } from './delete/interations-delete-dialog.component';
import { InterationsRoutingModule } from './route/interations-routing.module';

@NgModule({
  imports: [SharedModule, InterationsRoutingModule],
  declarations: [InterationsComponent, InterationsDetailComponent, InterationsUpdateComponent, InterationsDeleteDialogComponent],
})
export class InterationsModule {}
