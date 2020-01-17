import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';

import { RepositoryService } from './services/repository/repository.service';
import { UserService } from './services/users/user.service';

import { RegistrationComponent } from './registration/registration.component';

import {Authorized} from './guard/authorized.guard';
import {Admin} from './guard/admin.guard';
import {Notauthorized} from './guard/notauthorized.guard';
import { EmailComponent } from './email/email.component';
import { LoginComponent } from './login/login.component';
import {httpInterceptorProviders} from './services/users/auth-interceptor';
import { AdminComponent } from './admin/admin.component';
import { RecenzentComponent } from './recenzent/recenzent.component';

const ChildRoutes =
  [
  ]

  const RepositoryChildRoutes =
  [
    
  ]

const Routes = [
  {
    path: "registrate",
    component: RegistrationComponent
  },
  {
    path: "verify/:processId/:username",
    component: EmailComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "login",
    component: LoginComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "admin",
    component: AdminComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "recenzent",
    component: RecenzentComponent,
    canActivate: [Notauthorized]
  },

]

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    EmailComponent,
    LoginComponent,
    AdminComponent,
    RecenzentComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(Routes),
    HttpClientModule, 
    HttpModule
  ],
  
  providers:  [
    Admin,
    Authorized,
    Notauthorized,
    httpInterceptorProviders
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
