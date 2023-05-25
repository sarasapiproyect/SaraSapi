import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDefaultResponse } from '../default-response.model';
import { DefaultResponseService } from '../service/default-response.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './default-response-delete-dialog.component.html',
})
export class DefaultResponseDeleteDialogComponent {
  defaultResponse?: IDefaultResponse;

  constructor(protected defaultResponseService: DefaultResponseService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.defaultResponseService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
