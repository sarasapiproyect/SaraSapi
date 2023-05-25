import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChatbootStyle, NewChatbootStyle } from '../chatboot-style.model';

export type PartialUpdateChatbootStyle = Partial<IChatbootStyle> & Pick<IChatbootStyle, 'id'>;

export type EntityResponseType = HttpResponse<IChatbootStyle>;
export type EntityArrayResponseType = HttpResponse<IChatbootStyle[]>;

@Injectable({ providedIn: 'root' })
export class ChatbootStyleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/chatboot-styles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(chatbootStyle: NewChatbootStyle): Observable<EntityResponseType> {
    return this.http.post<IChatbootStyle>(this.resourceUrl, chatbootStyle, { observe: 'response' });
  }

  update(chatbootStyle: IChatbootStyle): Observable<EntityResponseType> {
    return this.http.put<IChatbootStyle>(`${this.resourceUrl}/${this.getChatbootStyleIdentifier(chatbootStyle)}`, chatbootStyle, {
      observe: 'response',
    });
  }

  partialUpdate(chatbootStyle: PartialUpdateChatbootStyle): Observable<EntityResponseType> {
    return this.http.patch<IChatbootStyle>(`${this.resourceUrl}/${this.getChatbootStyleIdentifier(chatbootStyle)}`, chatbootStyle, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChatbootStyle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChatbootStyle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getChatbootStyleIdentifier(chatbootStyle: Pick<IChatbootStyle, 'id'>): number {
    return chatbootStyle.id;
  }

  compareChatbootStyle(o1: Pick<IChatbootStyle, 'id'> | null, o2: Pick<IChatbootStyle, 'id'> | null): boolean {
    return o1 && o2 ? this.getChatbootStyleIdentifier(o1) === this.getChatbootStyleIdentifier(o2) : o1 === o2;
  }

  addChatbootStyleToCollectionIfMissing<Type extends Pick<IChatbootStyle, 'id'>>(
    chatbootStyleCollection: Type[],
    ...chatbootStylesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const chatbootStyles: Type[] = chatbootStylesToCheck.filter(isPresent);
    if (chatbootStyles.length > 0) {
      const chatbootStyleCollectionIdentifiers = chatbootStyleCollection.map(
        chatbootStyleItem => this.getChatbootStyleIdentifier(chatbootStyleItem)!
      );
      const chatbootStylesToAdd = chatbootStyles.filter(chatbootStyleItem => {
        const chatbootStyleIdentifier = this.getChatbootStyleIdentifier(chatbootStyleItem);
        if (chatbootStyleCollectionIdentifiers.includes(chatbootStyleIdentifier)) {
          return false;
        }
        chatbootStyleCollectionIdentifiers.push(chatbootStyleIdentifier);
        return true;
      });
      return [...chatbootStylesToAdd, ...chatbootStyleCollection];
    }
    return chatbootStyleCollection;
  }
}
