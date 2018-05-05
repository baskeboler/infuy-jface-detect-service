import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PersonGroupJface } from './person-group-jface.model';
import { PersonGroupJfacePopupService } from './person-group-jface-popup.service';
import { PersonGroupJfaceService } from './person-group-jface.service';

@Component({
    selector: 'jhi-person-group-jface-dialog',
    templateUrl: './person-group-jface-dialog.component.html'
})
export class PersonGroupJfaceDialogComponent implements OnInit {

    personGroup: PersonGroupJface;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private personGroupService: PersonGroupJfaceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personGroup.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personGroupService.update(this.personGroup));
        } else {
            this.subscribeToSaveResponse(
                this.personGroupService.create(this.personGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PersonGroupJface>>) {
        result.subscribe((res: HttpResponse<PersonGroupJface>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PersonGroupJface) {
        this.eventManager.broadcast({ name: 'personGroupListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-person-group-jface-popup',
    template: ''
})
export class PersonGroupJfacePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personGroupPopupService: PersonGroupJfacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.personGroupPopupService
                    .open(PersonGroupJfaceDialogComponent as Component, params['id']);
            } else {
                this.personGroupPopupService
                    .open(PersonGroupJfaceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
