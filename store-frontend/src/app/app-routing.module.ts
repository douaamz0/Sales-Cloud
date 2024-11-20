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
import {ProduitComponent} from "./components/produit/produit.component";
import {EditProduitComponent} from "./components/edit-produit/edit-produit.component";
import {AddProduitComponent} from "./components/add-produit/add-produit.component";
import {StatisticComponent} from "./components/statistic/statistic.component";
import {InvoiceComponent} from "./components/invoice/invoice.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'navbar', component: NavbarComponent, canActivate: [AuthGuard]},
  { path: 'clients', component: ClientListComponent , canActivate: [AuthGuard]},
  { path: 'add-client', component: ClientAddComponent, canActivate: [AuthGuard] },
  { path: 'edit-client/:id', component: ClientEditComponent, canActivate: [AuthGuard] },
  { path: 'produits', component: ProduitComponent, canActivate: [AuthGuard] },
  { path: 'edit-produit/:id', component: EditProduitComponent, canActivate: [AuthGuard] },
  { path: 'add-produit', component: AddProduitComponent, canActivate: [AuthGuard] },
  { path: 'ventes', component: VenteComponent, canActivate: [AuthGuard] },
  { path: 'ventes-add', component: AddEditVenteComponent, canActivate: [AuthGuard] },
  { path: 'vente-edit/:id', component: AddEditVenteComponent, canActivate: [AuthGuard] },
  { path: 'home', component: StatisticComponent ,canActivate: [AuthGuard] },
  { path: 'invoice/:id', component: InvoiceComponent ,canActivate: [AuthGuard] },

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
