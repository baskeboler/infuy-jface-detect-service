import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PersonGroupPersonJface } from './person-group-person-jface.model';
import { PersonGroupPersonJfaceService } from './person-group-person-jface.service';

@Injectable()
export class PersonGroupPersonJfacePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private personGroupPersonService: PersonGroupPersonJfaceService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.personGroupPersonService.find(id)
                    .subscribe((personGroupPersonResponse: HttpResponse<PersonGroupPersonJface>) => {
                        const personGroupPerson: PersonGroupPersonJface = personGroupPersonResponse.body;
                        this.ngbModalRef = this.personGroupPersonModalRef(component, personGroupPerson);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.personGroupPersonModalRef(component, new PersonGroupPersonJface());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    personGroupPersonModalRef(component: Component, personGroupPerson: PersonGroupPersonJface): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.personGroupPerson = personGroupPerson;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
