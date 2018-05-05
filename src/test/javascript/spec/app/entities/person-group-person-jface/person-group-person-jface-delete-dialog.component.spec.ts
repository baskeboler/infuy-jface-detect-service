/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterTestModule } from '../../../test.module';
import { PersonGroupPersonJfaceDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/person-group-person-jface/person-group-person-jface-delete-dialog.component';
import { PersonGroupPersonJfaceService } from '../../../../../../main/webapp/app/entities/person-group-person-jface/person-group-person-jface.service';

describe('Component Tests', () => {

    describe('PersonGroupPersonJface Management Delete Component', () => {
        let comp: PersonGroupPersonJfaceDeleteDialogComponent;
        let fixture: ComponentFixture<PersonGroupPersonJfaceDeleteDialogComponent>;
        let service: PersonGroupPersonJfaceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [PersonGroupPersonJfaceDeleteDialogComponent],
                providers: [
                    PersonGroupPersonJfaceService
                ]
            })
            .overrideTemplate(PersonGroupPersonJfaceDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonGroupPersonJfaceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonGroupPersonJfaceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
