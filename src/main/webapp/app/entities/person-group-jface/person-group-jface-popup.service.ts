import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PersonGroupJface } from './person-group-jface.model';
import { PersonGroupJfaceService } from './person-group-jface.service';

@Injectable()
export class PersonGroupJfacePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private personGroupService: PersonGroupJfaceService

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
                this.personGroupService.find(id)
                    .subscribe((personGroupResponse: HttpResponse<PersonGroupJface>) => {
                        const personGroup: PersonGroupJface = personGroupResponse.body;
                        this.ngbModalRef = this.personGroupModalRef(component, personGroup);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.personGroupModalRef(component, new PersonGroupJface());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    personGroupModalRef(component: Component, personGroup: PersonGroupJface): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.personGroup = personGroup;
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
