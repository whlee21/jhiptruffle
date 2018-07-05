import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPendingTransaction } from 'app/shared/model/pending-transaction.model';
import { PendingTransactionService } from './pending-transaction.service';

@Component({
    selector: 'jhi-pending-transaction-delete-dialog',
    templateUrl: './pending-transaction-delete-dialog.component.html'
})
export class PendingTransactionDeleteDialogComponent {
    pendingTransaction: IPendingTransaction;

    constructor(
        private pendingTransactionService: PendingTransactionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pendingTransactionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pendingTransactionListModification',
                content: 'Deleted an pendingTransaction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pending-transaction-delete-popup',
    template: ''
})
export class PendingTransactionDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pendingTransaction }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PendingTransactionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.pendingTransaction = pendingTransaction;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
