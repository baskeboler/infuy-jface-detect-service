import { BaseEntity } from './../../shared';

export class FaceJface implements BaseEntity {
    constructor(
        public id?: number,
        public persistedFaceId?: string,
        public personGroupPersonId?: number,
    ) {
    }
}
