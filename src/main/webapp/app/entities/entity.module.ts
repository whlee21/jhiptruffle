import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhiptruffleImageModule } from './image/image.module';
import { JhiptrufflePendingTransactionModule } from './pending-transaction/pending-transaction.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhiptruffleImageModule,
        JhiptrufflePendingTransactionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhiptruffleEntityModule {}
