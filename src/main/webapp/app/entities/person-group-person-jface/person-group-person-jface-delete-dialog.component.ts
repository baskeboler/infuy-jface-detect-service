import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PersonGroupPersonJface } from './person-group-person-jface.model';
import { PersonGroupPersonJfacePopupService } from './person-group-person-jface-popup.service';
import { PersonGroupPersonJfaceService } from './person-group-person-jface.service';

@Component({
    selector: 'jhi-person-group-person-jface-delete-dialog',
    templateUrl: './person-group-person-jface-delete-dialog.component.html'
})
export class PersonGroupPersonJfaceDeleteDialogComponent {

    personGroupPerson: PersonGroupPersonJface;

    constructor(
        private personGroupPersonService: PersonGroupPersonJfaceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personGroupPersonService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'personGroupPersonListModification',
                content: 'Deleted an personGroupPerson'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-person-group-person-jface-delete-popup',
    template: ''
})
export class PersonGroupPersonJfaceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personGroupPersonPopupService: PersonGroupPersonJfacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.personGroupPersonPopupService
                .open(PersonGroupPersonJfaceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
