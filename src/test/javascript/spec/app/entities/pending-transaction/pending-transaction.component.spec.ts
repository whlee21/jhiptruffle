/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhiptruffleTestModule } from '../../../test.module';
import { PendingTransactionComponent } from 'app/entities/pending-transaction/pending-transaction.component';
import { PendingTransactionService } from 'app/entities/pending-transaction/pending-transaction.service';
import { PendingTransaction } from 'app/shared/model/pending-transaction.model';

describe('Component Tests', () => {
    describe('PendingTransaction Management Component', () => {
        let comp: PendingTransactionComponent;
        let fixture: ComponentFixture<PendingTransactionComponent>;
        let service: PendingTransactionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhiptruffleTestModule],
                declarations: [PendingTransactionComponent],
                providers: []
            })
                .overrideTemplate(PendingTransactionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PendingTransactionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PendingTransactionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PendingTransaction(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pendingTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
