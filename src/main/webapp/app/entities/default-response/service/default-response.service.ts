import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDefaultResponse, NewDefaultResponse } from '../default-response.model';

export type PartialUpdateDefaultResponse = Partial<IDefaultResponse> & Pick<IDefaultResponse, 'id'>;

export type EntityResponseType = HttpResponse<IDefaultResponse>;
export type EntityArrayResponseType = HttpResponse<IDefaultResponse[]>;

@Injectable({ providedIn: 'root' })
export class DefaultResponseService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/default-responses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(defaultResponse: NewDefaultResponse): Observable<EntityResponseType> {
    return this.http.post<IDefaultResponse>(this.resourceUrl, defaultResponse, { observe: 'response' });
  }

  update(defaultResponse: IDefaultResponse): Observable<EntityResponseType> {
    return this.http.put<IDefaultResponse>(`${this.resourceUrl}/${this.getDefaultResponseIdentifier(defaultResponse)}`, defaultResponse, {
      observe: 'response',
    });
  }

  partialUpdate(defaultResponse: PartialUpdateDefaultResponse): Observable<EntityResponseType> {
    return this.http.patch<IDefaultResponse>(`${this.resourceUrl}/${this.getDefaultResponseIdentifier(defaultResponse)}`, defaultResponse, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDefaultResponse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDefaultResponse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDefaultResponseIdentifier(defaultResponse: Pick<IDefaultResponse, 'id'>): number {
    return defaultResponse.id;
  }

  compareDefaultResponse(o1: Pick<IDefaultResponse, 'id'> | null, o2: Pick<IDefaultResponse, 'id'> | null): boolean {
    return o1 && o2 ? this.getDefaultResponseIdentifier(o1) === this.getDefaultResponseIdentifier(o2) : o1 === o2;
  }

  addDefaultResponseToCollectionIfMissing<Type extends Pick<IDefaultResponse, 'id'>>(
    defaultResponseCollection: Type[],
    ...defaultResponsesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const defaultResponses: Type[] = defaultResponsesToCheck.filter(isPresent);
    if (defaultResponses.length > 0) {
      const defaultResponseCollectionIdentifiers = defaultResponseCollection.map(
        defaultResponseItem => this.getDefaultResponseIdentifier(defaultResponseItem)!
      );
      const defaultResponsesToAdd = defaultResponses.filter(defaultResponseItem => {
        const defaultResponseIdentifier = this.getDefaultResponseIdentifier(defaultResponseItem);
        if (defaultResponseCollectionIdentifiers.includes(defaultResponseIdentifier)) {
          return false;
        }
        defaultResponseCollectionIdentifiers.push(defaultResponseIdentifier);
        return true;
      });
      return [...defaultResponsesToAdd, ...defaultResponseCollection];
    }
    return defaultResponseCollection;
  }
}
