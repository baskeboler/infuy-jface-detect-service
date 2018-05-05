import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PersonGroupPersonJface } from './person-group-person-jface.model';
import { PersonGroupPersonJfacePopupService } from './person-group-person-jface-popup.service';
import { PersonGroupPersonJfaceService } from './person-group-person-jface.service';
import { PersonGroupJface, PersonGroupJfaceService } from '../person-group-jface';

@Component({
    selector: 'jhi-person-group-person-jface-dialog',
    templateUrl: './person-group-person-jface-dialog.component.html'
})
export class PersonGroupPersonJfaceDialogComponent implements OnInit {

    personGroupPerson: PersonGroupPersonJface;
    isSaving: boolean;

    persongroups: PersonGroupJface[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private personGroupPersonService: PersonGroupPersonJfaceService,
        private personGroupService: PersonGroupJfaceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.personGroupService.query()
            .subscribe((res: HttpResponse<PersonGroupJface[]>) => { this.persongroups = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personGroupPerson.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personGroupPersonService.update(this.personGroupPerson));
        } else {
            this.subscribeToSaveResponse(
                this.personGroupPersonService.create(this.personGroupPerson));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PersonGroupPersonJface>>) {
        result.subscribe((res: HttpResponse<PersonGroupPersonJface>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PersonGroupPersonJface) {
        this.eventManager.broadcast({ name: 'personGroupPersonListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPersonGroupById(index: number, item: PersonGroupJface) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-person-group-person-jface-popup',
    template: ''
})
export class PersonGroupPersonJfacePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personGroupPersonPopupService: PersonGroupPersonJfacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.personGroupPersonPopupService
                    .open(PersonGroupPersonJfaceDialogComponent as Component, params['id']);
            } else {
                this.personGroupPersonPopupService
                    .open(PersonGroupPersonJfaceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
