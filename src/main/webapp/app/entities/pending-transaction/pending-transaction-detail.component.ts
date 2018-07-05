import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPendingTransaction } from 'app/shared/model/pending-transaction.model';

@Component({
    selector: 'jhi-pending-transaction-detail',
    templateUrl: './pending-transaction-detail.component.html'
})
export class PendingTransactionDetailComponent implements OnInit {
    pendingTransaction: IPendingTransaction;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pendingTransaction }) => {
            this.pendingTransaction = pendingTransaction;
        });
    }

    previousState() {
        window.history.back();
    }
}
