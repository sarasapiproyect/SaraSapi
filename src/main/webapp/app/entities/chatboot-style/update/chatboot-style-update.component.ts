import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ChatbootStyleFormService, ChatbootStyleFormGroup } from './chatboot-style-form.service';
import { IChatbootStyle } from '../chatboot-style.model';
import { ChatbootStyleService } from '../service/chatboot-style.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-chatboot-style-update',
  templateUrl: './chatboot-style-update.component.html',
})
export class ChatbootStyleUpdateComponent implements OnInit {
  isSaving = false;
  chatbootStyle: IChatbootStyle | null = null;

  editForm: ChatbootStyleFormGroup = this.chatbootStyleFormService.createChatbootStyleFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected chatbootStyleService: ChatbootStyleService,
    protected chatbootStyleFormService: ChatbootStyleFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chatbootStyle }) => {
      this.chatbootStyle = chatbootStyle;
      if (chatbootStyle) {
        this.updateForm(chatbootStyle);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('saraBusinessInteligenteApp.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chatbootStyle = this.chatbootStyleFormService.getChatbootStyle(this.editForm);
    if (chatbootStyle.id !== null) {
      this.subscribeToSaveResponse(this.chatbootStyleService.update(chatbootStyle));
    } else {
      this.subscribeToSaveResponse(this.chatbootStyleService.create(chatbootStyle));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChatbootStyle>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(chatbootStyle: IChatbootStyle): void {
    this.chatbootStyle = chatbootStyle;
    this.chatbootStyleFormService.resetForm(this.editForm, chatbootStyle);
  }
}
