import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInterations, NewInterations } from '../interations.model';

export type PartialUpdateInterations = Partial<IInterations> & Pick<IInterations, 'id'>;

export type EntityResponseType = HttpResponse<IInterations>;
export type EntityArrayResponseType = HttpResponse<IInterations[]>;

@Injectable({ providedIn: 'root' })
export class InterationsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/interations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(interations: NewInterations): Observable<EntityResponseType> {
    return this.http.post<IInterations>(this.resourceUrl, interations, { observe: 'response' });
  }

  update(interations: IInterations): Observable<EntityResponseType> {
    return this.http.put<IInterations>(`${this.resourceUrl}/${this.getInterationsIdentifier(interations)}`, interations, {
      observe: 'response',
    });
  }

  partialUpdate(interations: PartialUpdateInterations): Observable<EntityResponseType> {
    return this.http.patch<IInterations>(`${this.resourceUrl}/${this.getInterationsIdentifier(interations)}`, interations, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInterations>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInterations[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInterationsIdentifier(interations: Pick<IInterations, 'id'>): number {
    return interations.id;
  }

  compareInterations(o1: Pick<IInterations, 'id'> | null, o2: Pick<IInterations, 'id'> | null): boolean {
    return o1 && o2 ? this.getInterationsIdentifier(o1) === this.getInterationsIdentifier(o2) : o1 === o2;
  }

  addInterationsToCollectionIfMissing<Type extends Pick<IInterations, 'id'>>(
    interationsCollection: Type[],
    ...interationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const interations: Type[] = interationsToCheck.filter(isPresent);
    if (interations.length > 0) {
      const interationsCollectionIdentifiers = interationsCollection.map(
        interationsItem => this.getInterationsIdentifier(interationsItem)!
      );
      const interationsToAdd = interations.filter(interationsItem => {
        const interationsIdentifier = this.getInterationsIdentifier(interationsItem);
        if (interationsCollectionIdentifiers.includes(interationsIdentifier)) {
          return false;
        }
        interationsCollectionIdentifiers.push(interationsIdentifier);
        return true;
      });
      return [...interationsToAdd, ...interationsCollection];
    }
    return interationsCollection;
  }
}
