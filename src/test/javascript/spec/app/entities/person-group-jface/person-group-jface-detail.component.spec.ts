/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterTestModule } from '../../../test.module';
import { PersonGroupJfaceDetailComponent } from '../../../../../../main/webapp/app/entities/person-group-jface/person-group-jface-detail.component';
import { PersonGroupJfaceService } from '../../../../../../main/webapp/app/entities/person-group-jface/person-group-jface.service';
import { PersonGroupJface } from '../../../../../../main/webapp/app/entities/person-group-jface/person-group-jface.model';

describe('Component Tests', () => {

    describe('PersonGroupJface Management Detail Component', () => {
        let comp: PersonGroupJfaceDetailComponent;
        let fixture: ComponentFixture<PersonGroupJfaceDetailComponent>;
        let service: PersonGroupJfaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [PersonGroupJfaceDetailComponent],
                providers: [
                    PersonGroupJfaceService
                ]
            })
            .overrideTemplate(PersonGroupJfaceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonGroupJfaceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonGroupJfaceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PersonGroupJface(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.personGroup).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
