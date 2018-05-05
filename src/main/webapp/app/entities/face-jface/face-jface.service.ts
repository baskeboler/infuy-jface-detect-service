import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { FaceJface } from './face-jface.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<FaceJface>;

@Injectable()
export class FaceJfaceService {

    private resourceUrl =  SERVER_API_URL + 'api/faces';

    constructor(private http: HttpClient) { }

    create(face: FaceJface): Observable<EntityResponseType> {
        const copy = this.convert(face);
        return this.http.post<FaceJface>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(face: FaceJface): Observable<EntityResponseType> {
        const copy = this.convert(face);
        return this.http.put<FaceJface>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<FaceJface>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<FaceJface[]>> {
        const options = createRequestOption(req);
        return this.http.get<FaceJface[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FaceJface[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: FaceJface = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<FaceJface[]>): HttpResponse<FaceJface[]> {
        const jsonResponse: FaceJface[] = res.body;
        const body: FaceJface[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to FaceJface.
     */
    private convertItemFromServer(face: FaceJface): FaceJface {
        const copy: FaceJface = Object.assign({}, face);
        return copy;
    }

    /**
     * Convert a FaceJface to a JSON which can be sent to the server.
     */
    private convert(face: FaceJface): FaceJface {
        const copy: FaceJface = Object.assign({}, face);
        return copy;
    }
}
