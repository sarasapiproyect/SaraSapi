import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ChatbootStyleComponent } from './list/chatboot-style.component';
import { ChatbootStyleDetailComponent } from './detail/chatboot-style-detail.component';
import { ChatbootStyleUpdateComponent } from './update/chatboot-style-update.component';
import { ChatbootStyleDeleteDialogComponent } from './delete/chatboot-style-delete-dialog.component';
import { ChatbootStyleRoutingModule } from './route/chatboot-style-routing.module';

@NgModule({
  imports: [SharedModule, ChatbootStyleRoutingModule],
  declarations: [ChatbootStyleComponent, ChatbootStyleDetailComponent, ChatbootStyleUpdateComponent, ChatbootStyleDeleteDialogComponent],
})
export class ChatbootStyleModule {}
