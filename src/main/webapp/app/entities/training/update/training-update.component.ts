import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TrainingFormService, TrainingFormGroup } from './training-form.service';
import { ITraining } from '../training.model';
import { TrainingService } from '../service/training.service';
import { SourceChannel } from 'app/entities/enumerations/source-channel.model';

@Component({
  selector: 'jhi-training-update',
  templateUrl: './training-update.component.html',
})
export class TrainingUpdateComponent implements OnInit {
  isSaving = false;
  training: ITraining | null = null;
  sourceChannelValues = Object.keys(SourceChannel);

  editForm: TrainingFormGroup = this.trainingFormService.createTrainingFormGroup();

  constructor(
    protected trainingService: TrainingService,
    protected trainingFormService: TrainingFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ training }) => {
      this.training = training;
      if (training) {
        this.updateForm(training);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const training = this.trainingFormService.getTraining(this.editForm);
    if (training.id !== null) {
      this.subscribeToSaveResponse(this.trainingService.update(training));
    } else {
      this.subscribeToSaveResponse(this.trainingService.create(training));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITraining>>): void {
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

  protected updateForm(training: ITraining): void {
    this.training = training;
    this.trainingFormService.resetForm(this.editForm, training);
  }
}
