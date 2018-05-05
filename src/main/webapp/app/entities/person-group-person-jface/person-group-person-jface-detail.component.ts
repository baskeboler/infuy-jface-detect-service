import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';
import {JhiEventManager} from 'ng-jhipster';

import {PersonGroupPersonJface} from './person-group-person-jface.model';
import {PersonGroupPersonJfaceService} from './person-group-person-jface.service';

@Component({
    selector: 'jhi-person-group-person-jface-detail',
    templateUrl: './person-group-person-jface-detail.component.html'
})
export class PersonGroupPersonJfaceDetailComponent implements OnInit, OnDestroy {

    personGroupPerson: PersonGroupPersonJface;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    faces: any[] = null;

    constructor(private eventManager: JhiEventManager,
                private personGroupPersonService: PersonGroupPersonJfaceService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.personGroupPersonService.faces()
            .map(faces => faces.map(f => {
                f.path = `/api/files/face-detection/${f.id}`;
                return f;
            }))
            .subscribe(faces => this.faces = faces);

        this.registerChangeInPersonGroupPeople();
    }

    load(id) {
        this.personGroupPersonService.find(id)
            .subscribe((personGroupPersonResponse: HttpResponse<PersonGroupPersonJface>) => {
                this.personGroupPerson = personGroupPersonResponse.body;
            });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonGroupPeople() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personGroupPersonListModification',
            (response) => this.load(this.personGroupPerson.id)
        );
    }

    submitFace(faceId: number) {
        console.log('submitting face');
        this.personGroupPersonService.submitFace(this.personGroupPerson.id,faceId)
            .subscribe(res => {
                console.log("sent face " + JSON.stringify(res));
            });
    }
}
