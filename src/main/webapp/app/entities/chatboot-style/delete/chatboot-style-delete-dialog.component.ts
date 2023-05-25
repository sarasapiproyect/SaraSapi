import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IChatbootStyle } from '../chatboot-style.model';
import { ChatbootStyleService } from '../service/chatboot-style.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './chatboot-style-delete-dialog.component.html',
})
export class ChatbootStyleDeleteDialogComponent {
  chatbootStyle?: IChatbootStyle;

  constructor(protected chatbootStyleService: ChatbootStyleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chatbootStyleService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
