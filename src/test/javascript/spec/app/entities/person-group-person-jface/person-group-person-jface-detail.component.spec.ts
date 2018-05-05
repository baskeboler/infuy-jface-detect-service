/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterTestModule } from '../../../test.module';
import { PersonGroupPersonJfaceDetailComponent } from '../../../../../../main/webapp/app/entities/person-group-person-jface/person-group-person-jface-detail.component';
import { PersonGroupPersonJfaceService } from '../../../../../../main/webapp/app/entities/person-group-person-jface/person-group-person-jface.service';
import { PersonGroupPersonJface } from '../../../../../../main/webapp/app/entities/person-group-person-jface/person-group-person-jface.model';

describe('Component Tests', () => {

    describe('PersonGroupPersonJface Management Detail Component', () => {
        let comp: PersonGroupPersonJfaceDetailComponent;
        let fixture: ComponentFixture<PersonGroupPersonJfaceDetailComponent>;
        let service: PersonGroupPersonJfaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [PersonGroupPersonJfaceDetailComponent],
                providers: [
                    PersonGroupPersonJfaceService
                ]
            })
            .overrideTemplate(PersonGroupPersonJfaceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonGroupPersonJfaceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonGroupPersonJfaceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PersonGroupPersonJface(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.personGroupPerson).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
