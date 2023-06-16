import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserExpresion, NewUserExpresion } from '../user-expresion.model';
import { HttpParams } from '@angular/common/http';


export type PartialUpdateUserExpresion = Partial<IUserExpresion> & Pick<IUserExpresion, 'id'>;

export type EntityResponseType = HttpResponse<IUserExpresion>;
export type EntityArrayResponseType = HttpResponse<IUserExpresion[]>;

@Injectable({ providedIn: 'root' })
export class UserExpresionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-expresions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userExpresion: NewUserExpresion): Observable<EntityResponseType> {
    return this.http.post<IUserExpresion>(this.resourceUrl, userExpresion, { observe: 'response' });
  }

  update(userExpresion: IUserExpresion): Observable<EntityResponseType> {
    return this.http.put<IUserExpresion>(`${this.resourceUrl}/${this.getUserExpresionIdentifier(userExpresion)}`, userExpresion, {
      observe: 'response',
    });
  }

  partialUpdate(userExpresion: PartialUpdateUserExpresion): Observable<EntityResponseType> {
    return this.http.patch<IUserExpresion>(`${this.resourceUrl}/${this.getUserExpresionIdentifier(userExpresion)}`, userExpresion, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserExpresion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserExpresion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryCombo(req?: any): Observable<EntityArrayResponseType> {
    const page = 0; // Número de página deseada
	const size = 150000; // Tamaño de página deseado

// Construir los parámetros de paginación
	let params = new HttpParams();
	params = params.set('page', page.toString());
	params = params.set('size', size.toString());
    return this.http.get<IUserExpresion[]>(this.resourceUrl, { params: params, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUserExpresionIdentifier(userExpresion: Pick<IUserExpresion, 'id'>): number {
    return userExpresion.id;
  }

  compareUserExpresion(o1: Pick<IUserExpresion, 'id'> | null, o2: Pick<IUserExpresion, 'id'> | null): boolean {
    return o1 && o2 ? this.getUserExpresionIdentifier(o1) === this.getUserExpresionIdentifier(o2) : o1 === o2;
  }

  addUserExpresionToCollectionIfMissing<Type extends Pick<IUserExpresion, 'id'>>(
    userExpresionCollection: Type[],
    ...userExpresionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const userExpresions: Type[] = userExpresionsToCheck.filter(isPresent);
    if (userExpresions.length > 0) {
      const userExpresionCollectionIdentifiers = userExpresionCollection.map(
        userExpresionItem => this.getUserExpresionIdentifier(userExpresionItem)!
      );
      const userExpresionsToAdd = userExpresions.filter(userExpresionItem => {
        const userExpresionIdentifier = this.getUserExpresionIdentifier(userExpresionItem);
        if (userExpresionCollectionIdentifiers.includes(userExpresionIdentifier)) {
          return false;
        }
        userExpresionCollectionIdentifiers.push(userExpresionIdentifier);
        return true;
      });
      return [...userExpresionsToAdd, ...userExpresionCollection];
    }
    return userExpresionCollection;
  }
}
