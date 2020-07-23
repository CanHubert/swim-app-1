import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {ProfileComponent} from './profile/profile.component';
import {BoardUserComponent} from './components/board-user/board-user.component';
import {BoardModeratorComponent} from './components/board-moderator/board-moderator.component';
import {BoardAdminComponent} from './components/board-admin/board-admin.component';
import {RegisterComponent} from './components/register/register.component';
import {CalendarComponent} from './calendar/calendar.component';
import {PriceListComponent} from './components/price-list/price-list.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'error', component: LoginComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'profile/:id', component: ProfileComponent},
  {path: 'user', component: BoardUserComponent},
  {path: 'mod', component: BoardModeratorComponent},
  {path: 'admin', component: BoardAdminComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'schedule', component: CalendarComponent},
  {path: 'prices', component: PriceListComponent},
  {path: '', redirectTo: '/' , pathMatch: 'full'},
  {path: '**', redirectTo: '/', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
