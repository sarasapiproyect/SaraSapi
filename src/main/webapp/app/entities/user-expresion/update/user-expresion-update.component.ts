import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { UserExpresionFormService, UserExpresionFormGroup } from './user-expresion-form.service';
import { IUserExpresion } from '../user-expresion.model';
import { UserExpresionService } from '../service/user-expresion.service';
import { Priority } from 'app/entities/enumerations/priority.model';

@Component({
  selector: 'jhi-user-expresion-update',
  templateUrl: './user-expresion-update.component.html',
})
export class UserExpresionUpdateComponent implements OnInit {
  isSaving = false;
  userExpresion: IUserExpresion | null = null;
  priorityValues = Object.keys(Priority);

  editForm: UserExpresionFormGroup = this.userExpresionFormService.createUserExpresionFormGroup();

  constructor(
    protected userExpresionService: UserExpresionService,
    protected userExpresionFormService: UserExpresionFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userExpresion }) => {
      this.userExpresion = userExpresion;
      if (userExpresion) {
        this.updateForm(userExpresion);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userExpresion = this.userExpresionFormService.getUserExpresion(this.editForm);
    if (userExpresion.id !== null) {
      this.subscribeToSaveResponse(this.userExpresionService.update(userExpresion));
    } else {
      this.subscribeToSaveResponse(this.userExpresionService.create(userExpresion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserExpresion>>): void {
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

  protected updateForm(userExpresion: IUserExpresion): void {
    this.userExpresion = userExpresion;
    this.userExpresionFormService.resetForm(this.editForm, userExpresion);
  }
}
