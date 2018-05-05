import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FaceJfaceComponent } from './face-jface.component';
import { FaceJfaceDetailComponent } from './face-jface-detail.component';
import { FaceJfacePopupComponent } from './face-jface-dialog.component';
import { FaceJfaceDeletePopupComponent } from './face-jface-delete-dialog.component';

@Injectable()
export class FaceJfaceResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const faceRoute: Routes = [
    {
        path: 'face-jface',
        component: FaceJfaceComponent,
        resolve: {
            'pagingParams': FaceJfaceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Faces'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'face-jface/:id',
        component: FaceJfaceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Faces'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const facePopupRoute: Routes = [
    {
        path: 'face-jface-new',
        component: FaceJfacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Faces'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'face-jface/:id/edit',
        component: FaceJfacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Faces'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'face-jface/:id/delete',
        component: FaceJfaceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Faces'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
