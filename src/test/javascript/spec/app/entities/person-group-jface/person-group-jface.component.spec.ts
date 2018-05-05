/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { PersonGroupJfaceComponent } from '../../../../../../main/webapp/app/entities/person-group-jface/person-group-jface.component';
import { PersonGroupJfaceService } from '../../../../../../main/webapp/app/entities/person-group-jface/person-group-jface.service';
import { PersonGroupJface } from '../../../../../../main/webapp/app/entities/person-group-jface/person-group-jface.model';

describe('Component Tests', () => {

    describe('PersonGroupJface Management Component', () => {
        let comp: PersonGroupJfaceComponent;
        let fixture: ComponentFixture<PersonGroupJfaceComponent>;
        let service: PersonGroupJfaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [PersonGroupJfaceComponent],
                providers: [
                    PersonGroupJfaceService
                ]
            })
            .overrideTemplate(PersonGroupJfaceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonGroupJfaceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonGroupJfaceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PersonGroupJface(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.personGroups[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
