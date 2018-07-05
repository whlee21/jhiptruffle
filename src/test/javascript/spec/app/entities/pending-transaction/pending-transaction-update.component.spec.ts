/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhiptruffleTestModule } from '../../../test.module';
import { PendingTransactionUpdateComponent } from 'app/entities/pending-transaction/pending-transaction-update.component';
import { PendingTransactionService } from 'app/entities/pending-transaction/pending-transaction.service';
import { PendingTransaction } from 'app/shared/model/pending-transaction.model';

describe('Component Tests', () => {
    describe('PendingTransaction Management Update Component', () => {
        let comp: PendingTransactionUpdateComponent;
        let fixture: ComponentFixture<PendingTransactionUpdateComponent>;
        let service: PendingTransactionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhiptruffleTestModule],
                declarations: [PendingTransactionUpdateComponent]
            })
                .overrideTemplate(PendingTransactionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PendingTransactionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PendingTransactionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PendingTransaction(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pendingTransaction = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PendingTransaction();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pendingTransaction = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
