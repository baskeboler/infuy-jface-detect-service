import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { FaceJface } from './face-jface.model';
import { FaceJfaceService } from './face-jface.service';

@Component({
    selector: 'jhi-face-jface-detail',
    templateUrl: './face-jface-detail.component.html'
})
export class FaceJfaceDetailComponent implements OnInit, OnDestroy {

    face: FaceJface;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private faceService: FaceJfaceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFaces();
    }

    load(id) {
        this.faceService.find(id)
            .subscribe((faceResponse: HttpResponse<FaceJface>) => {
                this.face = faceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFaces() {
        this.eventSubscriber = this.eventManager.subscribe(
            'faceListModification',
            (response) => this.load(this.face.id)
        );
    }
}
