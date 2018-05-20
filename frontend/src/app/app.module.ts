import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutes } from './app.routing';
import { SidebarModule } from './sidebar/sidebar.module';
import { FooterModule } from './shared/footer/footer.module';
import { NavbarModule} from './shared/navbar/navbar.module';
import { FixedPluginModule} from './shared/fixedplugin/fixedplugin.module';

import { DashboardComponent }   from './dashboard/dashboard.component';
import { UserComponent }   from './user/user.component';
import { TableComponent }   from './table/table.component';
import { TypographyComponent }   from './typography/typography.component';
import { IconsComponent }   from './icons/icons.component';
import { NotificationsComponent }   from './notifications/notifications.component';
import { UpgradeComponent }   from './upgrade/upgrade.component';
import { PulseComponent } from './examinations/pulse/pulse.component';
import { TemperatureComponent } from './examinations/temperature/temperature.component';
import { ExaminationsComponent } from './examinations/examinations.component';
import { DateFormComponent } from './examinations/date-form/date-form.component';
import { RegisterComponent } from './auth/register/register.component';
import { MyDoctorsComponent } from './mydoctors/mydoctors.component';

import { PatientService } from './services/patient.service';
import { AuthenticateComponent } from './auth/authenticate/authenticate.component';
import { AuthService } from './auth/authentication.service';
import { AuthGuardService } from './auth/auth-guard.service';
import { TokenInterceptorService } from './auth/token-interceptor.service';
import { ExaminationService } from './examinations/examinations.service';
import { MyDoctorsService } from './mydoctors/mydoctors.service';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    UserComponent,
    TableComponent,
    TypographyComponent,
    IconsComponent,
    NotificationsComponent,
    UpgradeComponent,
    PulseComponent,
    TemperatureComponent,
    ExaminationsComponent,
    DateFormComponent,
    RegisterComponent,
    AuthenticateComponent,
    MyDoctorsComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(AppRoutes),
    SidebarModule,
    NavbarModule,
    FooterModule,
    FixedPluginModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [PatientService, AuthService, AuthGuardService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass : TokenInterceptorService,
      multi : true
    }, ExaminationService, MyDoctorsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
