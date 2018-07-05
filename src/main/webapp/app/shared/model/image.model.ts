export interface IImage {
    id?: number;
    cryptoUser?: string;
    imageLocation?: string;
    upvoteCount?: number;
}

export class Image implements IImage {
    constructor(public id?: number, public cryptoUser?: string, public imageLocation?: string, public upvoteCount?: number) {}
}
