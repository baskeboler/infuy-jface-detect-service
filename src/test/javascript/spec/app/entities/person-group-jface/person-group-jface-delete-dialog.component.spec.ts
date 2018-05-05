/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterTestModule } from '../../../test.module';
import { PersonGroupJfaceDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/person-group-jface/person-group-jface-delete-dialog.component';
import { PersonGroupJfaceService } from '../../../../../../main/webapp/app/entities/person-group-jface/person-group-jface.service';

describe('Component Tests', () => {

    describe('PersonGroupJface Management Delete Component', () => {
        let comp: PersonGroupJfaceDeleteDialogComponent;
        let fixture: ComponentFixture<PersonGroupJfaceDeleteDialogComponent>;
        let service: PersonGroupJfaceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [PersonGroupJfaceDeleteDialogComponent],
                providers: [
                    PersonGroupJfaceService
                ]
            })
            .overrideTemplate(PersonGroupJfaceDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonGroupJfaceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonGroupJfaceService);
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
