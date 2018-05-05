import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FaceJface } from './face-jface.model';
import { FaceJfacePopupService } from './face-jface-popup.service';
import { FaceJfaceService } from './face-jface.service';
import { PersonGroupPersonJface, PersonGroupPersonJfaceService } from '../person-group-person-jface';

@Component({
    selector: 'jhi-face-jface-dialog',
    templateUrl: './face-jface-dialog.component.html'
})
export class FaceJfaceDialogComponent implements OnInit {

    face: FaceJface;
    isSaving: boolean;

    persongrouppeople: PersonGroupPersonJface[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private faceService: FaceJfaceService,
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
        if (this.face.id !== undefined) {
            this.subscribeToSaveResponse(
                this.faceService.update(this.face));
        } else {
            this.subscribeToSaveResponse(
                this.faceService.create(this.face));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<FaceJface>>) {
        result.subscribe((res: HttpResponse<FaceJface>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: FaceJface) {
        this.eventManager.broadcast({ name: 'faceListModification', content: 'OK'});
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
    selector: 'jhi-face-jface-popup',
    template: ''
})
export class FaceJfacePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facePopupService: FaceJfacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.facePopupService
                    .open(FaceJfaceDialogComponent as Component, params['id']);
            } else {
                this.facePopupService
                    .open(FaceJfaceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
