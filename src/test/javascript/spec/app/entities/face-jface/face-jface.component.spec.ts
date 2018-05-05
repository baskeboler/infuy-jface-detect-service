/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { FaceJfaceComponent } from '../../../../../../main/webapp/app/entities/face-jface/face-jface.component';
import { FaceJfaceService } from '../../../../../../main/webapp/app/entities/face-jface/face-jface.service';
import { FaceJface } from '../../../../../../main/webapp/app/entities/face-jface/face-jface.model';

describe('Component Tests', () => {

    describe('FaceJface Management Component', () => {
        let comp: FaceJfaceComponent;
        let fixture: ComponentFixture<FaceJfaceComponent>;
        let service: FaceJfaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [FaceJfaceComponent],
                providers: [
                    FaceJfaceService
                ]
            })
            .overrideTemplate(FaceJfaceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FaceJfaceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FaceJfaceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new FaceJface(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.faces[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
