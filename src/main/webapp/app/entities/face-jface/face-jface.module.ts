import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    FaceJfaceService,
    FaceJfacePopupService,
    FaceJfaceComponent,
    FaceJfaceDetailComponent,
    FaceJfaceDialogComponent,
    FaceJfacePopupComponent,
    FaceJfaceDeletePopupComponent,
    FaceJfaceDeleteDialogComponent,
    faceRoute,
    facePopupRoute,
    FaceJfaceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...faceRoute,
    ...facePopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FaceJfaceComponent,
        FaceJfaceDetailComponent,
        FaceJfaceDialogComponent,
        FaceJfaceDeleteDialogComponent,
        FaceJfacePopupComponent,
        FaceJfaceDeletePopupComponent,
    ],
    entryComponents: [
        FaceJfaceComponent,
        FaceJfaceDialogComponent,
        FaceJfacePopupComponent,
        FaceJfaceDeleteDialogComponent,
        FaceJfaceDeletePopupComponent,
    ],
    providers: [
        FaceJfaceService,
        FaceJfacePopupService,
        FaceJfaceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterFaceJfaceModule {}
