/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterTestModule } from '../../../test.module';
import { FaceJfaceDetailComponent } from '../../../../../../main/webapp/app/entities/face-jface/face-jface-detail.component';
import { FaceJfaceService } from '../../../../../../main/webapp/app/entities/face-jface/face-jface.service';
import { FaceJface } from '../../../../../../main/webapp/app/entities/face-jface/face-jface.model';

describe('Component Tests', () => {

    describe('FaceJface Management Detail Component', () => {
        let comp: FaceJfaceDetailComponent;
        let fixture: ComponentFixture<FaceJfaceDetailComponent>;
        let service: FaceJfaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [FaceJfaceDetailComponent],
                providers: [
                    FaceJfaceService
                ]
            })
            .overrideTemplate(FaceJfaceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FaceJfaceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FaceJfaceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new FaceJface(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.face).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
