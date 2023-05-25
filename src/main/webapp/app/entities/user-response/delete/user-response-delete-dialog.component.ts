import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserResponse } from '../user-response.model';
import { UserResponseService } from '../service/user-response.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './user-response-delete-dialog.component.html',
})
export class UserResponseDeleteDialogComponent {
  userResponse?: IUserResponse;

  constructor(protected userResponseService: UserResponseService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userResponseService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
