import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhiptruffleSharedModule } from 'app/shared';
import {
    ImageComponent,
    ImageDetailComponent,
    ImageUpdateComponent,
    ImageDeletePopupComponent,
    ImageDeleteDialogComponent,
    imageRoute,
    imagePopupRoute
} from './';
import { DisplayImageComponent } from './display-image/display-image.component';

const ENTITY_STATES = [...imageRoute, ...imagePopupRoute];

@NgModule({
    imports: [JhiptruffleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ImageComponent,
        ImageDetailComponent,
        ImageUpdateComponent,
        ImageDeleteDialogComponent,
        ImageDeletePopupComponent,
        DisplayImageComponent
    ],
    entryComponents: [ImageComponent, ImageUpdateComponent, ImageDeleteDialogComponent, ImageDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhiptruffleImageModule {}
