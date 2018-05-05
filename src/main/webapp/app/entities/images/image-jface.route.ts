import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ImageJfaceComponent } from './image-jface.component';
import { ImageJfaceDetailComponent } from './image-jface-detail.component';
import { ImageJfacePopupComponent } from './image-jface-dialog.component';
import { ImageJfaceDeletePopupComponent } from './image-jface-delete-dialog.component';

@Injectable()
export class ImageJfaceResolvePagingParams implements Resolve<any> {

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

export const imageRoute: Routes = [
    {
        path: 'image-jface',
        component: ImageJfaceComponent,
        resolve: {
            'pagingParams': ImageJfaceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Faces'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'image-jface/:id',
        component: ImageJfaceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Images'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imagePopupRoute: Routes = [
    {
        path: 'image-jface-new',
        component: ImageJfacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Images'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'image-jface/:id/edit',
        component: ImageJfacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Faces'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'image-jface/:id/delete',
        component: ImageJfaceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Faces'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
