import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    PersonGroupJfaceService,
    PersonGroupJfacePopupService,
    PersonGroupJfaceComponent,
    PersonGroupJfaceDetailComponent,
    PersonGroupJfaceDialogComponent,
    PersonGroupJfacePopupComponent,
    PersonGroupJfaceDeletePopupComponent,
    PersonGroupJfaceDeleteDialogComponent,
    personGroupRoute,
    personGroupPopupRoute,
    PersonGroupJfaceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...personGroupRoute,
    ...personGroupPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PersonGroupJfaceComponent,
        PersonGroupJfaceDetailComponent,
        PersonGroupJfaceDialogComponent,
        PersonGroupJfaceDeleteDialogComponent,
        PersonGroupJfacePopupComponent,
        PersonGroupJfaceDeletePopupComponent,
    ],
    entryComponents: [
        PersonGroupJfaceComponent,
        PersonGroupJfaceDialogComponent,
        PersonGroupJfacePopupComponent,
        PersonGroupJfaceDeleteDialogComponent,
        PersonGroupJfaceDeletePopupComponent,
    ],
    providers: [
        PersonGroupJfaceService,
        PersonGroupJfacePopupService,
        PersonGroupJfaceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterPersonGroupJfaceModule {}
