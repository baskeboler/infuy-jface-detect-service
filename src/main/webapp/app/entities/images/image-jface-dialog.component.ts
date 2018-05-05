import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ImageJface } from './image-jface.model';
import { ImageJfacePopupService } from './image-jface-popup.service';
import { ImageJfaceService } from './image-jface.service';
import { PersonGroupPersonJface, PersonGroupPersonJfaceService } from '../person-group-person-jface';

@Component({
    selector: 'jhi-image-jface-dialog',
    templateUrl: './image-jface-dialog.component.html'
})
export class ImageJfaceDialogComponent implements OnInit {

    image: ImageJface;
    isSaving: boolean;

    persongrouppeople: PersonGroupPersonJface[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private imageService: ImageJfaceService,
        private personGroupPersonService: PersonGroupPersonJfaceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.personGroupPersonService.query()
            .subscribe((res: HttpResponse<PersonGroupPersonJface[]>) => { this.persongrouppeople = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.image.id !== undefined) {
            this.subscribeToSaveResponse(
                this.imageService.update(this.image));
        } else {
            this.subscribeToSaveResponse(
                this.imageService.create(this.image));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ImageJface>>) {
        result.subscribe((res: HttpResponse<ImageJface>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ImageJface) {
        this.eventManager.broadcast({ name: 'imageListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPersonGroupPersonById(index: number, item: PersonGroupPersonJface) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-image-jface-popup',
    template: ''
})
export class ImageJfacePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imagePopupService: ImageJfacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.imagePopupService
                    .open(ImageJfaceDialogComponent as Component, params['id']);
            } else {
                this.imagePopupService
                    .open(ImageJfaceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
