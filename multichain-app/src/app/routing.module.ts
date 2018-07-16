import {NgModule} from '@angular/core';

import {RouterModule, Routes} from '@angular/router';
import {RequestListComponent} from './components/request-list/request-list.component';
import {RequestNewComponent} from './components/request-new/request-new.component';
import {DecisionListComponent} from './components/decision-list/decision-list.component';
import {LoginComponent} from './components/login/login.component';
import {HomeComponent} from './components/home/home.component';
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {ChainExplorerComponent} from './components/chain-explorer/chain-explorer.component';
import {UserListComponent} from './components/user-list/user-list.component';

const appRoutes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'requests', component: RequestListComponent},
  {path: 'requests/new', component: RequestNewComponent},
  {path: 'decisions', component: DecisionListComponent},
  {path: 'myprofile', component: UserProfileComponent},
  {path: 'users', component: UserListComponent},
  {path: 'chainexplorer', component: ChainExplorerComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
