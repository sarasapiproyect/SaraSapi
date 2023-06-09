import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { UserResponseFormService, UserResponseFormGroup } from './user-response-form.service';
import { IUserResponse } from '../user-response.model';
import { UserResponseService } from '../service/user-response.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IChannel } from 'app/entities/channel/channel.model';
import { ChannelService } from 'app/entities/channel/service/channel.service';
import { Priority } from 'app/entities/enumerations/priority.model';
import { ResponseType } from 'app/entities/enumerations/response-type.model';
import { MultimediaType } from 'app/entities/enumerations/multimedia-type.model';

@Component({
  selector: 'jhi-user-response-update',
  templateUrl: './user-response-update.component.html',
})
export class UserResponseUpdateComponent implements OnInit {
  isSaving = false;
  userResponse: IUserResponse | null = null;
  priorityValues = Object.keys(Priority);
  responseTypeValues = Object.keys(ResponseType);
  multimediaTypeValues = Object.keys(MultimediaType);

  channelsSharedCollection: IChannel[] = [];

  editForm: UserResponseFormGroup = this.userResponseFormService.createUserResponseFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected userResponseService: UserResponseService,
    protected userResponseFormService: UserResponseFormService,
    protected channelService: ChannelService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareChannel = (o1: IChannel | null, o2: IChannel | null): boolean => this.channelService.compareChannel(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userResponse }) => {
      this.userResponse = userResponse;
      if (userResponse) {
        this.updateForm(userResponse);
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
    const userResponse = this.userResponseFormService.getUserResponse(this.editForm);
    if (userResponse.id !== null) {
      this.subscribeToSaveResponse(this.userResponseService.update(userResponse));
    } else {
      this.subscribeToSaveResponse(this.userResponseService.create(userResponse));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserResponse>>): void {
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

  protected updateForm(userResponse: IUserResponse): void {
    this.userResponse = userResponse;
    this.userResponseFormService.resetForm(this.editForm, userResponse);

    this.channelsSharedCollection = this.channelService.addChannelToCollectionIfMissing<IChannel>(
      this.channelsSharedCollection,
      ...(userResponse.channelMultimedias ?? []),
      ...(userResponse.channelVoices ?? []),
      ...(userResponse.channelAnimations ?? [])
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
            ...(this.userResponse?.channelMultimedias ?? []),
            ...(this.userResponse?.channelVoices ?? []),
            ...(this.userResponse?.channelAnimations ?? [])
          )
        )
      )
      .subscribe((channels: IChannel[]) => (this.channelsSharedCollection = channels));
  }
}
