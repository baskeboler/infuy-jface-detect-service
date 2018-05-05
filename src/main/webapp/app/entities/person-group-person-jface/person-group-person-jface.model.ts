import { BaseEntity } from './../../shared';

export class PersonGroupPersonJface implements BaseEntity {
    constructor(
        public id?: number,
        public personId?: string,
        public name?: string,
        public description?: string,
        public personGroupId?: number,
        public faces?: BaseEntity[],
    ) {
    }
}
