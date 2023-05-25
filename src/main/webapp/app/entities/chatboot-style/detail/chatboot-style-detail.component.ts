import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChatbootStyle } from '../chatboot-style.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-chatboot-style-detail',
  templateUrl: './chatboot-style-detail.component.html',
})
export class ChatbootStyleDetailComponent implements OnInit {
  chatbootStyle: IChatbootStyle | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chatbootStyle }) => {
      this.chatbootStyle = chatbootStyle;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
