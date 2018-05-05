import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    PersonGroupPersonJfaceService,
    PersonGroupPersonJfacePopupService,
    PersonGroupPersonJfaceComponent,
    PersonGroupPersonJfaceDetailComponent,
    PersonGroupPersonJfaceDialogComponent,
    PersonGroupPersonJfacePopupComponent,
    PersonGroupPersonJfaceDeletePopupComponent,
    PersonGroupPersonJfaceDeleteDialogComponent,
    personGroupPersonRoute,
    personGroupPersonPopupRoute,
    PersonGroupPersonJfaceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...personGroupPersonRoute,
    ...personGroupPersonPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PersonGroupPersonJfaceComponent,
        PersonGroupPersonJfaceDetailComponent,
        PersonGroupPersonJfaceDialogComponent,
        PersonGroupPersonJfaceDeleteDialogComponent,
        PersonGroupPersonJfacePopupComponent,
        PersonGroupPersonJfaceDeletePopupComponent,
    ],
    entryComponents: [
        PersonGroupPersonJfaceComponent,
        PersonGroupPersonJfaceDialogComponent,
        PersonGroupPersonJfacePopupComponent,
        PersonGroupPersonJfaceDeleteDialogComponent,
        PersonGroupPersonJfaceDeletePopupComponent,
    ],
    providers: [
        PersonGroupPersonJfaceService,
        PersonGroupPersonJfacePopupService,
        PersonGroupPersonJfaceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterPersonGroupPersonJfaceModule {}
