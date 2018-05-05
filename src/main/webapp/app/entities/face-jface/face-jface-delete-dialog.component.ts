import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FaceJface } from './face-jface.model';
import { FaceJfacePopupService } from './face-jface-popup.service';
import { FaceJfaceService } from './face-jface.service';

@Component({
    selector: 'jhi-face-jface-delete-dialog',
    templateUrl: './face-jface-delete-dialog.component.html'
})
export class FaceJfaceDeleteDialogComponent {

    face: FaceJface;

    constructor(
        private faceService: FaceJfaceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.faceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'faceListModification',
                content: 'Deleted an face'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-face-jface-delete-popup',
    template: ''
})
export class FaceJfaceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facePopupService: FaceJfacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.facePopupService
                .open(FaceJfaceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
