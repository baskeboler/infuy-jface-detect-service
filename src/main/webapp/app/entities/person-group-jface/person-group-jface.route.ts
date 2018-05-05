import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PersonGroupJfaceComponent } from './person-group-jface.component';
import { PersonGroupJfaceDetailComponent } from './person-group-jface-detail.component';
import { PersonGroupJfacePopupComponent } from './person-group-jface-dialog.component';
import { PersonGroupJfaceDeletePopupComponent } from './person-group-jface-delete-dialog.component';

@Injectable()
export class PersonGroupJfaceResolvePagingParams implements Resolve<any> {

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

export const personGroupRoute: Routes = [
    {
        path: 'person-group-jface',
        component: PersonGroupJfaceComponent,
        resolve: {
            'pagingParams': PersonGroupJfaceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonGroups'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'person-group-jface/:id',
        component: PersonGroupJfaceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonGroups'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personGroupPopupRoute: Routes = [
    {
        path: 'person-group-jface-new',
        component: PersonGroupJfacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'person-group-jface/:id/edit',
        component: PersonGroupJfacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'person-group-jface/:id/delete',
        component: PersonGroupJfaceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
