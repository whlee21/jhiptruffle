import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IImage, Image } from 'app/shared/model/image.model';
import { Principal } from 'app/core';
import { ImageService } from './image.service';
import { Web3Service } from 'app/util/web3.service';
import { ResponseWrapper } from 'app/shared/model/response-wrapper.model';
import { Observable } from 'rxjs/Observable';

@Component({
    selector: 'jhi-image',
    templateUrl: './image.component.html',
    styleUrls: ['album.css']
})
export class ImageComponent implements OnInit, OnDestroy {
    // images: IImage[];
    // currentAccount: any;
    // eventSubscriber: Subscription;
    //
    // constructor(
    //     private imageService: ImageService,
    //     private jhiAlertService: JhiAlertService,
    //     private eventManager: JhiEventManager,
    //     private principal: Principal
    // ) {}
    //
    // loadAll() {
    //     this.imageService.query().subscribe(
    //         (res: HttpResponse<IImage[]>) => {
    //             this.images = res.body;
    //         },
    //         (res: HttpErrorResponse) => this.onError(res.message)
    //     );
    // }
    //
    // ngOnInit() {
    //     this.loadAll();
    //     this.principal.identity().then(account => {
    //         this.currentAccount = account;
    //     });
    //     this.registerChangeInImages();
    // }
    //
    // ngOnDestroy() {
    //     this.eventManager.destroy(this.eventSubscriber);
    // }
    //
    // trackId(index: number, item: IImage) {
    //     return item.id;
    // }
    //
    // registerChangeInImages() {
    //     this.eventSubscriber = this.eventManager.subscribe('imageListModification', response => this.loadAll());
    // }
    //
    // private onError(errorMessage: string) {
    //     this.jhiAlertService.error(errorMessage, null, null);
    // }
    accounts: string[];
    images: IImage[];
    currentAccount: any;
    eventSubscriber: Subscription;
    url = require('../../../content/images/rainbowskele.jpg');
    imageBlob;
    uploadedImage;

    ethereumModel = {
        amount: 0,
        receiver: '',
        balance: '',
        account: ''
    };

    constructor(
        private imageService: ImageService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private web3Service: Web3Service
    ) {}

    loadAll() {
        // this.imageService.query().subscribe(
        //     (res: ResponseWrapper) => {
        //         this.images = res.json;
        //     },
        //     (res: ResponseWrapper) => this.onError(res.json)
        // );
        this.imageService.query().subscribe(
            (res: HttpResponse<IImage[]>) => {
                this.images = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInImages();
        this.watchAccount();
    }

    watchAccount() {
        this.web3Service.accountsObservable.subscribe(accounts => {
            this.accounts = accounts;
            this.ethereumModel.account = accounts[0];
            this.refreshBalance();
        });
    }

    refreshBalance() {
        let that = this;
        try {
            this.web3Service.getEthBalance(this.ethereumModel.account, function(data) {
                that.ethereumModel.balance = data;
            });
        } catch (e) {
            console.log(e);
        }
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IImage) {
        return item.id;
    }

    registerChangeInImages() {
        this.eventSubscriber = this.eventManager.subscribe('imageListModification', response => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    onSelectFile(event) {
        if (event.target.files && event.target.files[0]) {
            this.uploadedImage = event.target.files[0];

            const blob = new Blob([event.target.files[0]], { type: 'image/jpeg' });
            const blobUrl = URL.createObjectURL(blob);

            const reader = new FileReader();
            reader.readAsDataURL(event.target.files[0]); // read file as data url
            reader.onload = (event: any) => {
                // called once readAsDataURL is completed
                this.url = event.target.result;
                try {
                    localStorage.setItem('filething', this.uploadedImage);
                } catch (e) {
                    console.log('Storage failed: ' + e);
                }
            };
        }
    }

    submitImage(event) {
        console.log('in submitImage() attempting to upload image:' + this.uploadedImage);

        const imageModel = new Image(null, this.ethereumModel.account, 'img/location.jpg', 1, this.url);

        this.subscribeToSaveResponse(this.imageService.create(imageModel));
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IImage>>) {
        result.subscribe((res: HttpResponse<IImage>) => this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<IImage>) {
        this.eventManager.broadcast({ name: 'imageListModification', content: 'OK' });
    }

    private onSaveError() {}
}
