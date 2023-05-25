import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { InterationsFormService, InterationsFormGroup } from './interations-form.service';
import { IInterations } from '../interations.model';
import { InterationsService } from '../service/interations.service';
import { SourceChannel } from 'app/entities/enumerations/source-channel.model';

@Component({
  selector: 'jhi-interations-update',
  templateUrl: './interations-update.component.html',
})
export class InterationsUpdateComponent implements OnInit {
  isSaving = false;
  interations: IInterations | null = null;
  sourceChannelValues = Object.keys(SourceChannel);

  editForm: InterationsFormGroup = this.interationsFormService.createInterationsFormGroup();

  constructor(
    protected interationsService: InterationsService,
    protected interationsFormService: InterationsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interations }) => {
      this.interations = interations;
      if (interations) {
        this.updateForm(interations);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const interations = this.interationsFormService.getInterations(this.editForm);
    if (interations.id !== null) {
      this.subscribeToSaveResponse(this.interationsService.update(interations));
    } else {
      this.subscribeToSaveResponse(this.interationsService.create(interations));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterations>>): void {
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

  protected updateForm(interations: IInterations): void {
    this.interations = interations;
    this.interationsFormService.resetForm(this.editForm, interations);
  }
}
