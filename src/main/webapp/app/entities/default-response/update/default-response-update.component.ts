import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DefaultResponseFormService, DefaultResponseFormGroup } from './default-response-form.service';
import { IDefaultResponse } from '../default-response.model';
import { DefaultResponseService } from '../service/default-response.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IChannel } from 'app/entities/channel/channel.model';
import { ChannelService } from 'app/entities/channel/service/channel.service';
import { Priority } from 'app/entities/enumerations/priority.model';
import { MultimediaType } from 'app/entities/enumerations/multimedia-type.model';

@Component({
  selector: 'jhi-default-response-update',
  templateUrl: './default-response-update.component.html',
})
export class DefaultResponseUpdateComponent implements OnInit {
  isSaving = false;
  defaultResponse: IDefaultResponse | null = null;
  priorityValues = Object.keys(Priority);
  multimediaTypeValues = Object.keys(MultimediaType);

  channelsSharedCollection: IChannel[] = [];

  editForm: DefaultResponseFormGroup = this.defaultResponseFormService.createDefaultResponseFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected defaultResponseService: DefaultResponseService,
    protected defaultResponseFormService: DefaultResponseFormService,
    protected channelService: ChannelService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareChannel = (o1: IChannel | null, o2: IChannel | null): boolean => this.channelService.compareChannel(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ defaultResponse }) => {
      this.defaultResponse = defaultResponse;
      if (defaultResponse) {
        this.updateForm(defaultResponse);
      }

      this.loadRelationshipsOptions();
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const defaultResponse = this.defaultResponseFormService.getDefaultResponse(this.editForm);
    if (defaultResponse.id !== null) {
      this.subscribeToSaveResponse(this.defaultResponseService.update(defaultResponse));
    } else {
      this.subscribeToSaveResponse(this.defaultResponseService.create(defaultResponse));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDefaultResponse>>): void {
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

  protected updateForm(defaultResponse: IDefaultResponse): void {
    this.defaultResponse = defaultResponse;
    this.defaultResponseFormService.resetForm(this.editForm, defaultResponse);

    this.channelsSharedCollection = this.channelService.addChannelToCollectionIfMissing<IChannel>(
      this.channelsSharedCollection,
      ...(defaultResponse.channelMultimedias ?? []),
      ...(defaultResponse.channelVoices ?? []),
      ...(defaultResponse.channelAnimations ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.channelService
      .query()
      .pipe(map((res: HttpResponse<IChannel[]>) => res.body ?? []))
      .pipe(
        map((channels: IChannel[]) =>
          this.channelService.addChannelToCollectionIfMissing<IChannel>(
            channels,
            ...(this.defaultResponse?.channelMultimedias ?? []),
            ...(this.defaultResponse?.channelVoices ?? []),
            ...(this.defaultResponse?.channelAnimations ?? [])
          )
        )
      )
      .subscribe((channels: IChannel[]) => (this.channelsSharedCollection = channels));
  }
}
