import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterPersonGroupJfaceModule } from './person-group-jface/person-group-jface.module';
import { JhipsterPersonGroupPersonJfaceModule } from './person-group-person-jface/person-group-person-jface.module';
import { JhipsterFaceJfaceModule } from './face-jface/face-jface.module';
import {JhipsterImageJfaceModule} from './images/image-jface.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterPersonGroupJfaceModule,
        JhipsterPersonGroupPersonJfaceModule,
        JhipsterFaceJfaceModule,
        JhipsterImageJfaceModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterEntityModule {}
