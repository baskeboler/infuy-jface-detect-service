import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ImageJface } from './image-jface.model';
import { ImageJfacePopupService } from './image-jface-popup.service';
import { ImageJfaceService } from './image-jface.service';

@Component({
    selector: 'jhi-image-jface-delete-dialog',
    templateUrl: './image-jface-delete-dialog.component.html'
})
export class ImageJfaceDeleteDialogComponent {

    face: ImageJface;

    constructor(
        private faceService: ImageJfaceService,
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
    selector: 'jhi-image-jface-delete-popup',
    template: ''
})
export class ImageJfaceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facePopupService: ImageJfacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.facePopupService
                .open(ImageJfaceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
