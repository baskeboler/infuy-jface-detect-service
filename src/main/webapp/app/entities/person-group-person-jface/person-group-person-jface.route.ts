import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PersonGroupPersonJfaceComponent } from './person-group-person-jface.component';
import { PersonGroupPersonJfaceDetailComponent } from './person-group-person-jface-detail.component';
import { PersonGroupPersonJfacePopupComponent } from './person-group-person-jface-dialog.component';
import { PersonGroupPersonJfaceDeletePopupComponent } from './person-group-person-jface-delete-dialog.component';

@Injectable()
export class PersonGroupPersonJfaceResolvePagingParams implements Resolve<any> {

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

export const personGroupPersonRoute: Routes = [
    {
        path: 'person-group-person-jface',
        component: PersonGroupPersonJfaceComponent,
        resolve: {
            'pagingParams': PersonGroupPersonJfaceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonGroupPeople'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'person-group-person-jface/:id',
        component: PersonGroupPersonJfaceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonGroupPeople'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personGroupPersonPopupRoute: Routes = [
    {
        path: 'person-group-person-jface-new',
        component: PersonGroupPersonJfacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonGroupPeople'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'person-group-person-jface/:id/edit',
        component: PersonGroupPersonJfacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonGroupPeople'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'person-group-person-jface/:id/delete',
        component: PersonGroupPersonJfaceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonGroupPeople'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
