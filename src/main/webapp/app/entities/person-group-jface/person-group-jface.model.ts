import { BaseEntity } from './../../shared';

export class PersonGroupJface implements BaseEntity {
    constructor(
        public id?: number,
        public personGroupId?: string,
        public name?: string,
        public description?: string,
        public people?: BaseEntity[],
    ) {
    }
}

export interface TrainingStatus {
    status: string;
    createdDateTime: string;
    lastActionDateTime: string;
    message: string;
}
