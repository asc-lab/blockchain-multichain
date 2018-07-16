import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {SuiModule} from "ng2-semantic-ui";

import {AppComponent} from './app.component';
import {MenuComponent} from './components/menu/menu.component';
import {FooterComponent} from './components/footer/footer.component';
import {RequestNewComponent} from './components/request-new/request-new.component';
import {RequestListComponent} from './components/request-list/request-list.component';
import {DecisionListComponent} from './components/decision-list/decision-list.component';
import {AppRoutingModule} from "./routing.module";
import {LoginComponent} from './components/login/login.component';
import {AuthInterceptorService} from "./shared/services/auth/auth-interceptor.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {SimpleNotificationsModule} from "angular2-notifications";
import {AuthService} from "./shared/services/auth/auth.service";
import {NotificationService} from "./shared/notification.service";
import {HomeComponent} from './components/home/home.component';
import {CookieService} from "ngx-cookie-service";
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {ChainExplorerComponent} from './components/chain-explorer/chain-explorer.component';
import {UserListComponent} from './components/user-list/user-list.component';
import {PetitionFullInfoPipe} from "./shared/pipes/petition-full-info.pipe";
import {SplitPipe} from "./shared/pipes/split.pipe";
import { MenuMobileItemsComponent } from './components/menu-mobile-items/menu-mobile-items.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    FooterComponent,
    RequestNewComponent,
    RequestListComponent,
    DecisionListComponent,
    LoginComponent,
    HomeComponent,
    UserProfileComponent,
    ChainExplorerComponent,
    UserListComponent,
    PetitionFullInfoPipe,
    SplitPipe,
    MenuMobileItemsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    SimpleNotificationsModule.forRoot({
      showProgressBar: true,
      preventLastDuplicates: true,
      clickIconToClose: true,
      timeOut: 5000,
      position: ["top", "right"]
    }),
    HttpClientModule,
    SuiModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    },
    AuthService,
    NotificationService,
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
