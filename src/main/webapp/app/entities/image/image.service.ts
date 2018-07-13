import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IImage } from 'app/shared/model/image.model';
import { ResponseWrapper } from 'app/shared/model/response-wrapper.model';

type EntityResponseType = HttpResponse<IImage>;
type EntityArrayResponseType = HttpResponse<IImage[]>;

@Injectable({ providedIn: 'root' })
export class ImageService {
    private resourceUrl = SERVER_API_URL + 'api/images';

    constructor(private http: HttpClient) {}

    create(image: IImage): Observable<EntityResponseType> {
        const copy = this.convert(image);
        console.log('in create', copy);
        return this.http.post<IImage>(this.resourceUrl, copy, { observe: 'response' });
        // return this.http.post<IImage>(this.resourceUrl, copy, { observe: 'response' }).map((res: Response) => {
        //     const jsonResponse = res.json();
        //     return this.convertItemFromServer(jsonResponse);
        // });
    }

    update(image: IImage): Observable<EntityResponseType> {
        return this.http.put<IImage>(this.resourceUrl, image, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IImage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IImage[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
    // private resourceUrl = SERVER_API_URL + 'api/images';
    //
    // constructor(private http: HttpClient) { }
    //
    // create(image: IImage): Observable<IImage> {
    //     console.log('in create');
    //     const copy = this.convert(image);
    //     return this.http.post(this.resourceUrl, copy).map((res: Response) => {
    //         const jsonResponse = res.json();
    //         return this.convertItemFromServer(jsonResponse);
    //     });
    // }
    //
    // update(image: IImage): Observable<IImage> {
    //     const copy = this.convert(image);
    //     return this.http.put(this.resourceUrl, copy).map((res: Response) => {
    //         const jsonResponse = res.json();
    //         return this.convertItemFromServer(jsonResponse);
    //     });
    // }
    //
    // find(id: number): Observable<IImage> {
    //     return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
    //         const jsonResponse = res.json();
    //         return this.convertItemFromServer(jsonResponse);
    //     });
    // }
    //
    // query(req?: any): Observable<ResponseWrapper> {
    //     const options = createRequestOption(req);
    //     return this.http.get(this.resourceUrl, options)
    //         .map((res: Response) => this.convertResponse(res));
    // }
    //
    // delete(id: number): Observable<Response> {
    //     return this.http.delete(`${this.resourceUrl}/${id}`);
    // }
    //
    // private convertResponse(res: Response): ResponseWrapper {
    //     const jsonResponse = res.json();
    //     const result = [];
    //     for (let i = 0; i < jsonResponse.length; i++) {
    //         result.push(this.convertItemFromServer(jsonResponse[i]));
    //     }
    //     return new ResponseWrapper(res.headers, result, res.status);
    // }
    //
    // /**
    //  * Convert a returned JSON object to Image.
    //  */
    private convertItemFromServer(json: any): IImage {
        const entity: IImage = Object.assign(new Image(), json);
        return entity;
    }

    /**
     * Convert a Image to a JSON which can be sent to the server.
     */
    private convert(image: IImage): IImage {
        const copy: IImage = Object.assign({}, image);
        return copy;
    }
}
