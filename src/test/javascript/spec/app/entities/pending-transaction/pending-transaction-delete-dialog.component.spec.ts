/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhiptruffleTestModule } from '../../../test.module';
import { PendingTransactionDeleteDialogComponent } from 'app/entities/pending-transaction/pending-transaction-delete-dialog.component';
import { PendingTransactionService } from 'app/entities/pending-transaction/pending-transaction.service';

describe('Component Tests', () => {
    describe('PendingTransaction Management Delete Component', () => {
        let comp: PendingTransactionDeleteDialogComponent;
        let fixture: ComponentFixture<PendingTransactionDeleteDialogComponent>;
        let service: PendingTransactionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhiptruffleTestModule],
                declarations: [PendingTransactionDeleteDialogComponent]
            })
                .overrideTemplate(PendingTransactionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PendingTransactionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PendingTransactionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
