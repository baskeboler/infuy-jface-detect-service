import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PersonGroupPersonJface } from './person-group-person-jface.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PersonGroupPersonJface>;

@Injectable()
export class PersonGroupPersonJfaceService {

    private resourceUrl =  SERVER_API_URL + 'api/person-group-people';

    constructor(private http: HttpClient) { }

    faces(): Observable<any[]> {
        return this.http.get<any[]>('/api/face-detection?only-valid=true');
    }

    submitFace(personId: number, faceId: number): Observable<any> {
        const obj = {
            faceDetectionId: faceId,
            personId: personId
        };
        return this.http.post('/api/person-face-submissions', obj);
    }

    create(personGroupPerson: PersonGroupPersonJface): Observable<EntityResponseType> {
        const copy = this.convert(personGroupPerson);
        return this.http.post<PersonGroupPersonJface>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(personGroupPerson: PersonGroupPersonJface): Observable<EntityResponseType> {
        const copy = this.convert(personGroupPerson);
        return this.http.put<PersonGroupPersonJface>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PersonGroupPersonJface>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PersonGroupPersonJface[]>> {
        const options = createRequestOption(req);
        return this.http.get<PersonGroupPersonJface[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PersonGroupPersonJface[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PersonGroupPersonJface = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PersonGroupPersonJface[]>): HttpResponse<PersonGroupPersonJface[]> {
        const jsonResponse: PersonGroupPersonJface[] = res.body;
        const body: PersonGroupPersonJface[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PersonGroupPersonJface.
     */
    private convertItemFromServer(personGroupPerson: PersonGroupPersonJface): PersonGroupPersonJface {
        const copy: PersonGroupPersonJface = Object.assign({}, personGroupPerson);
        return copy;
    }

    /**
     * Convert a PersonGroupPersonJface to a JSON which can be sent to the server.
     */
    private convert(personGroupPerson: PersonGroupPersonJface): PersonGroupPersonJface {
        const copy: PersonGroupPersonJface = Object.assign({}, personGroupPerson);
        return copy;
    }
}
