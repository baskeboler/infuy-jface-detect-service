import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    ImageJfaceService,
    ImageJfacePopupService,
    ImageJfaceComponent,
    ImageJfaceDetailComponent,
    ImageJfaceDialogComponent,
    ImageJfacePopupComponent,
    ImageJfaceDeletePopupComponent,
    ImageJfaceDeleteDialogComponent,
    imageRoute,
    imagePopupRoute,
    ImageJfaceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...imageRoute,
    ...imagePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ImageJfaceComponent,
        ImageJfaceDetailComponent,
        ImageJfaceDialogComponent,
        ImageJfaceDeleteDialogComponent,
        ImageJfacePopupComponent,
        ImageJfaceDeletePopupComponent,
    ],
    entryComponents: [
        ImageJfaceComponent,
        ImageJfaceDialogComponent,
        ImageJfacePopupComponent,
        ImageJfaceDeleteDialogComponent,
        ImageJfaceDeletePopupComponent,
    ],
    providers: [
        ImageJfaceService,
        ImageJfacePopupService,
        ImageJfaceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterImageJfaceModule {}
