import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientListComponent } from './components/client-list/client-list.component';
import { ClientAddComponent } from './components/client-add/client-add.component';
import { ClientEditComponent } from './components/client-edit/client-edit.component';
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {AuthGuard} from "./guards/auth.guard";
import {AddEditVenteComponent} from "./components/add-edit-vente/add-edit-vente.component";
import {VenteComponent} from "./components/vente/vente.component";
import {NavbarComponent} from "./components/navbar/navbar.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'navbar', component: NavbarComponent, canActivate: [AuthGuard]},
  { path: 'clients', component: ClientListComponent,canActivate: [AuthGuard]},
  { path: 'add-client', component: ClientAddComponent },
  { path: 'edit-client/:id', component: ClientEditComponent },

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
