import { IImage } from 'app/shared/model//image.model';

export interface IPendingTransaction {
    id?: number;
    sender?: string;
    receiver?: string;
    tokenQuantity?: number;
    transactionHash?: string;
    image?: IImage;
}

export class PendingTransaction implements IPendingTransaction {
    constructor(
        public id?: number,
        public sender?: string,
        public receiver?: string,
        public tokenQuantity?: number,
        public transactionHash?: string,
        public imageId?: number
    ) {}
}
