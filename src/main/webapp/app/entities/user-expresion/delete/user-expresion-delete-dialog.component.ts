import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserExpresion } from '../user-expresion.model';
import { UserExpresionService } from '../service/user-expresion.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './user-expresion-delete-dialog.component.html',
})
export class UserExpresionDeleteDialogComponent {
  userExpresion?: IUserExpresion;

  constructor(protected userExpresionService: UserExpresionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userExpresionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
