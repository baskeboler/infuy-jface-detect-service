import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ImageJface } from './image-jface.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ImageJface>;

@Injectable()
export class ImageJfaceService {

    private resourceUrl =  SERVER_API_URL + 'api/images';

    constructor(private http: HttpClient) { }

    create(image: ImageJface): Observable<EntityResponseType> {
        const copy = this.convert(image);
        return this.http.post<ImageJface>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(image: ImageJface): Observable<EntityResponseType> {
        const copy = this.convert(image);
        return this.http.put<ImageJface>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ImageJface>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    faces(id: number): Observable<Object[]> {
        return this.http.get<Object[]>(`${this.resourceUrl}/${id}/faces`);
    }

    query(req?: any): Observable<HttpResponse<ImageJface[]>> {
        const options = createRequestOption(req);
        return this.http.get<ImageJface[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ImageJface[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ImageJface = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ImageJface[]>): HttpResponse<ImageJface[]> {
        const jsonResponse: ImageJface[] = res.body;
        const body: ImageJface[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ImageJface.
     */
    private convertItemFromServer(image: ImageJface): ImageJface {
        const copy: ImageJface = Object.assign({}, image);
        return copy;
    }

    /**
     * Convert a ImageJface to a JSON which can be sent to the server.
     */
    private convert(image: ImageJface): ImageJface {
        const copy: ImageJface = Object.assign({}, image);
        return copy;
    }
}
