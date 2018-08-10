import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';

import { MetaSenderComponent } from './meta-sender.component';

export const metaSenderRoute: Route = {
    path: 'meta-sender',
    component: MetaSenderComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Meta Sender'
    },
    canActivate: [UserRouteAccessService]
};
