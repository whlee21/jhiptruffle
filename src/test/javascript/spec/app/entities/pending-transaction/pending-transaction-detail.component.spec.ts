/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhiptruffleTestModule } from '../../../test.module';
import { PendingTransactionDetailComponent } from 'app/entities/pending-transaction/pending-transaction-detail.component';
import { PendingTransaction } from 'app/shared/model/pending-transaction.model';

describe('Component Tests', () => {
    describe('PendingTransaction Management Detail Component', () => {
        let comp: PendingTransactionDetailComponent;
        let fixture: ComponentFixture<PendingTransactionDetailComponent>;
        const route = ({ data: of({ pendingTransaction: new PendingTransaction(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhiptruffleTestModule],
                declarations: [PendingTransactionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PendingTransactionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PendingTransactionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pendingTransaction).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
