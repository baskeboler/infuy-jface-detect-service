/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { PersonGroupPersonJfaceComponent } from '../../../../../../main/webapp/app/entities/person-group-person-jface/person-group-person-jface.component';
import { PersonGroupPersonJfaceService } from '../../../../../../main/webapp/app/entities/person-group-person-jface/person-group-person-jface.service';
import { PersonGroupPersonJface } from '../../../../../../main/webapp/app/entities/person-group-person-jface/person-group-person-jface.model';

describe('Component Tests', () => {

    describe('PersonGroupPersonJface Management Component', () => {
        let comp: PersonGroupPersonJfaceComponent;
        let fixture: ComponentFixture<PersonGroupPersonJfaceComponent>;
        let service: PersonGroupPersonJfaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [PersonGroupPersonJfaceComponent],
                providers: [
                    PersonGroupPersonJfaceService
                ]
            })
            .overrideTemplate(PersonGroupPersonJfaceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonGroupPersonJfaceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonGroupPersonJfaceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PersonGroupPersonJface(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.personGroupPeople[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
