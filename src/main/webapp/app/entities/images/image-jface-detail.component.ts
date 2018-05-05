import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ImageJface } from './image-jface.model';
import { ImageJfaceService } from './image-jface.service';

@Component({
    selector: 'jhi-image-jface-detail',
    templateUrl: './image-jface-detail.component.html'
})
export class ImageJfaceDetailComponent implements OnInit, OnDestroy {

    image: ImageJface;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    faces: any[] = null;

    constructor(
        private eventManager: JhiEventManager,
        private imageService: ImageJfaceService,
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
        this.imageService.find(id)
            .subscribe((imageResponse: HttpResponse<ImageJface>) => {
                this.image = imageResponse.body;
            });
        this.imageService.faces(id)
            .map<any,any>(faces => faces.map(f => {
                f.path = '/api/files/face-detection/' + f.id;
                return f;
            }))
            .subscribe(faces => {
                this.faces = faces;
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
            (response) => this.load(this.image.id)
        );
    }
}
