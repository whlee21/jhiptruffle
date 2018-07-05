import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPendingTransaction } from 'app/shared/model/pending-transaction.model';
import { PendingTransactionService } from './pending-transaction.service';
import { IImage } from 'app/shared/model/image.model';
import { ImageService } from 'app/entities/image';

@Component({
    selector: 'jhi-pending-transaction-update',
    templateUrl: './pending-transaction-update.component.html'
})
export class PendingTransactionUpdateComponent implements OnInit {
    private _pendingTransaction: IPendingTransaction;
    isSaving: boolean;

    images: IImage[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private pendingTransactionService: PendingTransactionService,
        private imageService: ImageService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pendingTransaction }) => {
            this.pendingTransaction = pendingTransaction;
        });
        this.imageService.query().subscribe(
            (res: HttpResponse<IImage[]>) => {
                this.images = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pendingTransaction.id !== undefined) {
            this.subscribeToSaveResponse(this.pendingTransactionService.update(this.pendingTransaction));
        } else {
            this.subscribeToSaveResponse(this.pendingTransactionService.create(this.pendingTransaction));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPendingTransaction>>) {
        result.subscribe((res: HttpResponse<IPendingTransaction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackImageById(index: number, item: IImage) {
        return item.id;
    }
    get pendingTransaction() {
        return this._pendingTransaction;
    }

    set pendingTransaction(pendingTransaction: IPendingTransaction) {
        this._pendingTransaction = pendingTransaction;
    }
}
