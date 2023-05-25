import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInterations } from '../interations.model';

@Component({
  selector: 'jhi-interations-detail',
  templateUrl: './interations-detail.component.html',
})
export class InterationsDetailComponent implements OnInit {
  interations: IInterations | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interations }) => {
      this.interations = interations;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
