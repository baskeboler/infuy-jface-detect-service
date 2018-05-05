import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import {PersonGroupJface, TrainingStatus} from './person-group-jface.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PersonGroupJface>;

@Injectable()
export class PersonGroupJfaceService {

    private resourceUrl =  SERVER_API_URL + 'api/person-groups';

    constructor(private http: HttpClient) { }

    create(personGroup: PersonGroupJface): Observable<EntityResponseType> {
        const copy = this.convert(personGroup);
        return this.http.post<PersonGroupJface>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(personGroup: PersonGroupJface): Observable<EntityResponseType> {
        const copy = this.convert(personGroup);
        return this.http.put<PersonGroupJface>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PersonGroupJface>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PersonGroupJface[]>> {
        const options = createRequestOption(req);
        return this.http.get<PersonGroupJface[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PersonGroupJface[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    train(id: number): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.resourceUrl}/${id}/train`, null);
    }

    getTrainingStatus(id: number): Observable<TrainingStatus> {
        return this.http.get<TrainingStatus>(`${this.resourceUrl}/${id}/training-status`);
    }
    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PersonGroupJface = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PersonGroupJface[]>): HttpResponse<PersonGroupJface[]> {
        const jsonResponse: PersonGroupJface[] = res.body;
        const body: PersonGroupJface[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PersonGroupJface.
     */
    private convertItemFromServer(personGroup: PersonGroupJface): PersonGroupJface {
        const copy: PersonGroupJface = Object.assign({}, personGroup);
        return copy;
    }

    /**
     * Convert a PersonGroupJface to a JSON which can be sent to the server.
     */
    private convert(personGroup: PersonGroupJface): PersonGroupJface {
        const copy: PersonGroupJface = Object.assign({}, personGroup);
        return copy;
    }
}
