import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';
import {JhiEventManager} from 'ng-jhipster';

import {PersonGroupJface, TrainingStatus} from './person-group-jface.model';
import {PersonGroupJfaceService} from './person-group-jface.service';

@Component({
    selector: 'jhi-person-group-jface-detail',
    templateUrl: './person-group-jface-detail.component.html'
})
export class PersonGroupJfaceDetailComponent implements OnInit, OnDestroy {

    personGroup: PersonGroupJface;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    trainingStatus: TrainingStatus = null;

    constructor(private eventManager: JhiEventManager,
                private personGroupService: PersonGroupJfaceService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });

        this.registerChangeInPersonGroups();
    }

    train() {
         if (this.personGroup != null) {
             this.personGroupService.train(this.personGroup.id)
                 .subscribe(val => {
                     if (val.ok) {
                         console.log('response ok');
                     } else {
                         console.log('train response not ok');
                     }
                 })
         }
    }

    load(id) {
        this.personGroupService.find(id)
            .subscribe((personGroupResponse: HttpResponse<PersonGroupJface>) => {
                this.personGroup = personGroupResponse.body;
            });

        this.personGroupService.getTrainingStatus(id)
            .subscribe(status => this.trainingStatus = status);
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonGroups() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personGroupListModification',
            (response) => this.load(this.personGroup.id)
        );
    }
}
