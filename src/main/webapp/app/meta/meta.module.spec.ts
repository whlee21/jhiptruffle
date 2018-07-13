import { MetaModule } from './meta.module';

describe('MetaModule', () => {
    let metaModule: MetaModule;

    beforeEach(() => {
        metaModule = new MetaModule();
    });

    it('should create an instance', () => {
        expect(metaModule).toBeTruthy();
    });
});
