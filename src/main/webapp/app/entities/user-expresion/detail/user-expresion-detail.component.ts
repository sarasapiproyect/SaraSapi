import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserExpresion } from '../user-expresion.model';

@Component({
  selector: 'jhi-user-expresion-detail',
  templateUrl: './user-expresion-detail.component.html',
})
export class UserExpresionDetailComponent implements OnInit {
  userExpresion: IUserExpresion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userExpresion }) => {
      this.userExpresion = userExpresion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
