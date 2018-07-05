import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPendingTransaction } from 'app/shared/model/pending-transaction.model';

type EntityResponseType = HttpResponse<IPendingTransaction>;
type EntityArrayResponseType = HttpResponse<IPendingTransaction[]>;

@Injectable({ providedIn: 'root' })
export class PendingTransactionService {
    private resourceUrl = SERVER_API_URL + 'api/pending-transactions';

    constructor(private http: HttpClient) {}

    create(pendingTransaction: IPendingTransaction): Observable<EntityResponseType> {
        return this.http.post<IPendingTransaction>(this.resourceUrl, pendingTransaction, { observe: 'response' });
    }

    update(pendingTransaction: IPendingTransaction): Observable<EntityResponseType> {
        return this.http.put<IPendingTransaction>(this.resourceUrl, pendingTransaction, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPendingTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPendingTransaction[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
