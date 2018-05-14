import { Routes } from '@angular/router';

import { DashboardComponent }   from './dashboard/dashboard.component';
import { UserComponent }   from './user/user.component';
import { TableComponent }   from './table/table.component';
import { TypographyComponent }   from './typography/typography.component';
import { IconsComponent }   from './icons/icons.component';
import { NotificationsComponent }   from './notifications/notifications.component';
import { UpgradeComponent }   from './upgrade/upgrade.component';
import { ExaminationsComponent } from './examinations/examinations.component';
import { RegisterComponent } from './auth/register/register.component';
import { AuthenticateComponent } from './auth/authenticate/authenticate.component';
import { AuthGuardService } from './auth/auth-guard.service';

export const AppRoutes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
    },
    {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate : [AuthGuardService]
    },
    {
        path: 'user',
        component: UserComponent
    },
    {
        path: 'table',
        component: TableComponent
    },
    {
        path: 'typography',
        component: TypographyComponent
    },
    {
        path: 'icons',
        component: IconsComponent
    },
    {
        path: 'notifications',
        component: NotificationsComponent
    },
    {
        path: 'examinations',
        component: ExaminationsComponent,
        canActivate : [AuthGuardService]
    },
    {
        path: 'register',
        component: RegisterComponent
    },
    {
        path: 'login',
        component: AuthenticateComponent
    }
]
