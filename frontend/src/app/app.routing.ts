import { Routes } from '@angular/router';

import { DashboardComponent }   from './dashboard/dashboard.component';
import { MyDoctorsComponent } from './mydoctors/mydoctors.component';
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
import { MyAccountComponent } from './myaccount/myaccount.component';
import { MyPatientsComponent } from './doctor/my-patients/my-patients.component';
import { PatientDetailsComponent } from './doctor/my-patients/patient-details/patient-details.component';
import { AddPatientComponent } from './doctor/add-patient/add-patient.component';

export const AppRoutes: Routes = [
    {
        path: '',
        redirectTo: 'examinations',
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
    },
    {
        path: 'mydoctors',
        component: MyDoctorsComponent,
        canActivate: [AuthGuardService]
    },
    {
        path: 'myaccount',
        component: MyAccountComponent,
        canActivate: [AuthGuardService]
    },
    {
        path: 'mypatients',
        component: MyPatientsComponent,
        canActivate: [AuthGuardService]
    }, 
    {
        path: 'mypatients/:username',
        component: PatientDetailsComponent,
        canActivate: [AuthGuardService]
    },
    {
        path: 'addpatient',
        component: AddPatientComponent,
        canActivate: [AuthGuardService]
    }
]
