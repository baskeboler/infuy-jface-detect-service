import { BaseEntity } from './../../shared';

export class ImageJface implements BaseEntity {
    constructor(
        public id?: number,
        public path?: string,
        public data?: string
    ) {
    }
}
