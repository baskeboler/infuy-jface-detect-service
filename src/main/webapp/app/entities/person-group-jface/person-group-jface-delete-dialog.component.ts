import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PersonGroupJface } from './person-group-jface.model';
import { PersonGroupJfacePopupService } from './person-group-jface-popup.service';
import { PersonGroupJfaceService } from './person-group-jface.service';

@Component({
    selector: 'jhi-person-group-jface-delete-dialog',
    templateUrl: './person-group-jface-delete-dialog.component.html'
})
export class PersonGroupJfaceDeleteDialogComponent {

    personGroup: PersonGroupJface;

    constructor(
        private personGroupService: PersonGroupJfaceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personGroupService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'personGroupListModification',
                content: 'Deleted an personGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-person-group-jface-delete-popup',
    template: ''
})
export class PersonGroupJfaceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personGroupPopupService: PersonGroupJfacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.personGroupPopupService
                .open(PersonGroupJfaceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
