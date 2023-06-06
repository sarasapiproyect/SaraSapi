import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserResponse, NewUserResponse } from '../user-response.model';
import { HttpParams } from '@angular/common/http';

export type PartialUpdateUserResponse = Partial<IUserResponse> & Pick<IUserResponse, 'id'>;

export type EntityResponseType = HttpResponse<IUserResponse>;
export type EntityArrayResponseType = HttpResponse<IUserResponse[]>;

@Injectable({ providedIn: 'root' })
export class UserResponseService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-responses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userResponse: NewUserResponse): Observable<EntityResponseType> {
    return this.http.post<IUserResponse>(this.resourceUrl, userResponse, { observe: 'response' });
  }

  update(userResponse: IUserResponse): Observable<EntityResponseType> {
    return this.http.put<IUserResponse>(`${this.resourceUrl}/${this.getUserResponseIdentifier(userResponse)}`, userResponse, {
      observe: 'response',
    });
  }

  partialUpdate(userResponse: PartialUpdateUserResponse): Observable<EntityResponseType> {
    return this.http.patch<IUserResponse>(`${this.resourceUrl}/${this.getUserResponseIdentifier(userResponse)}`, userResponse, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserResponse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserResponse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryCombo(req?: any): Observable<EntityArrayResponseType> {
    const page = 0; // Número de página deseada
	const size = 150; // Tamaño de página deseado

// Construir los parámetros de paginación
	let params = new HttpParams();
	params = params.set('page', page.toString());
	params = params.set('size', size.toString());
    return this.http.get<IUserResponse[]>(this.resourceUrl, { params: params, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUserResponseIdentifier(userResponse: Pick<IUserResponse, 'id'>): number {
    return userResponse.id;
  }

  compareUserResponse(o1: Pick<IUserResponse, 'id'> | null, o2: Pick<IUserResponse, 'id'> | null): boolean {
    return o1 && o2 ? this.getUserResponseIdentifier(o1) === this.getUserResponseIdentifier(o2) : o1 === o2;
  }

  addUserResponseToCollectionIfMissing<Type extends Pick<IUserResponse, 'id'>>(
    userResponseCollection: Type[],
    ...userResponsesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const userResponses: Type[] = userResponsesToCheck.filter(isPresent);
    if (userResponses.length > 0) {
      const userResponseCollectionIdentifiers = userResponseCollection.map(
        userResponseItem => this.getUserResponseIdentifier(userResponseItem)!
      );
      const userResponsesToAdd = userResponses.filter(userResponseItem => {
        const userResponseIdentifier = this.getUserResponseIdentifier(userResponseItem);
        if (userResponseCollectionIdentifiers.includes(userResponseIdentifier)) {
          return false;
        }
        userResponseCollectionIdentifiers.push(userResponseIdentifier);
        return true;
      });
      return [...userResponsesToAdd, ...userResponseCollection];
    }
    return userResponseCollection;
  }
}
