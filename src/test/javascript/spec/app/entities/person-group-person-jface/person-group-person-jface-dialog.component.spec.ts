/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterTestModule } from '../../../test.module';
import { PersonGroupPersonJfaceDialogComponent } from '../../../../../../main/webapp/app/entities/person-group-person-jface/person-group-person-jface-dialog.component';
import { PersonGroupPersonJfaceService } from '../../../../../../main/webapp/app/entities/person-group-person-jface/person-group-person-jface.service';
import { PersonGroupPersonJface } from '../../../../../../main/webapp/app/entities/person-group-person-jface/person-group-person-jface.model';
import { PersonGroupJfaceService } from '../../../../../../main/webapp/app/entities/person-group-jface';

describe('Component Tests', () => {

    describe('PersonGroupPersonJface Management Dialog Component', () => {
        let comp: PersonGroupPersonJfaceDialogComponent;
        let fixture: ComponentFixture<PersonGroupPersonJfaceDialogComponent>;
        let service: PersonGroupPersonJfaceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [PersonGroupPersonJfaceDialogComponent],
                providers: [
                    PersonGroupJfaceService,
                    PersonGroupPersonJfaceService
                ]
            })
            .overrideTemplate(PersonGroupPersonJfaceDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonGroupPersonJfaceDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonGroupPersonJfaceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PersonGroupPersonJface(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.personGroupPerson = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'personGroupPersonListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PersonGroupPersonJface();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.personGroupPerson = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'personGroupPersonListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
