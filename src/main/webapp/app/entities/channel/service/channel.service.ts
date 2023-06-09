import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChannel, NewChannel } from '../channel.model';

export type PartialUpdateChannel = Partial<IChannel> & Pick<IChannel, 'id'>;

export type EntityResponseType = HttpResponse<IChannel>;
export type EntityArrayResponseType = HttpResponse<IChannel[]>;

@Injectable({ providedIn: 'root' })
export class ChannelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/channels');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(channel: NewChannel): Observable<EntityResponseType> {
    return this.http.post<IChannel>(this.resourceUrl, channel, { observe: 'response' });
  }

  update(channel: IChannel): Observable<EntityResponseType> {
    return this.http.put<IChannel>(`${this.resourceUrl}/${this.getChannelIdentifier(channel)}`, channel, { observe: 'response' });
  }

  partialUpdate(channel: PartialUpdateChannel): Observable<EntityResponseType> {
    return this.http.patch<IChannel>(`${this.resourceUrl}/${this.getChannelIdentifier(channel)}`, channel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChannel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChannel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getChannelIdentifier(channel: Pick<IChannel, 'id'>): number {
    return channel.id;
  }

  compareChannel(o1: Pick<IChannel, 'id'> | null, o2: Pick<IChannel, 'id'> | null): boolean {
    return o1 && o2 ? this.getChannelIdentifier(o1) === this.getChannelIdentifier(o2) : o1 === o2;
  }

  addChannelToCollectionIfMissing<Type extends Pick<IChannel, 'id'>>(
    channelCollection: Type[],
    ...channelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const channels: Type[] = channelsToCheck.filter(isPresent);
    if (channels.length > 0) {
      const channelCollectionIdentifiers = channelCollection.map(channelItem => this.getChannelIdentifier(channelItem)!);
      const channelsToAdd = channels.filter(channelItem => {
        const channelIdentifier = this.getChannelIdentifier(channelItem);
        if (channelCollectionIdentifiers.includes(channelIdentifier)) {
          return false;
        }
        channelCollectionIdentifiers.push(channelIdentifier);
        return true;
      });
      return [...channelsToAdd, ...channelCollection];
    }
    return channelCollection;
  }
}
