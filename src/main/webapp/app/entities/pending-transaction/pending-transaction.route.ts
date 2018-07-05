import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PendingTransaction } from 'app/shared/model/pending-transaction.model';
import { PendingTransactionService } from './pending-transaction.service';
import { PendingTransactionComponent } from './pending-transaction.component';
import { PendingTransactionDetailComponent } from './pending-transaction-detail.component';
import { PendingTransactionUpdateComponent } from './pending-transaction-update.component';
import { PendingTransactionDeletePopupComponent } from './pending-transaction-delete-dialog.component';
import { IPendingTransaction } from 'app/shared/model/pending-transaction.model';

@Injectable({ providedIn: 'root' })
export class PendingTransactionResolve implements Resolve<IPendingTransaction> {
    constructor(private service: PendingTransactionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pendingTransaction: HttpResponse<PendingTransaction>) => pendingTransaction.body));
        }
        return of(new PendingTransaction());
    }
}

export const pendingTransactionRoute: Routes = [
    {
        path: 'pending-transaction',
        component: PendingTransactionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhiptruffleApp.pendingTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pending-transaction/:id/view',
        component: PendingTransactionDetailComponent,
        resolve: {
            pendingTransaction: PendingTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhiptruffleApp.pendingTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pending-transaction/new',
        component: PendingTransactionUpdateComponent,
        resolve: {
            pendingTransaction: PendingTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhiptruffleApp.pendingTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pending-transaction/:id/edit',
        component: PendingTransactionUpdateComponent,
        resolve: {
            pendingTransaction: PendingTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhiptruffleApp.pendingTransaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pendingTransactionPopupRoute: Routes = [
    {
        path: 'pending-transaction/:id/delete',
        component: PendingTransactionDeletePopupComponent,
        resolve: {
            pendingTransaction: PendingTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhiptruffleApp.pendingTransaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
