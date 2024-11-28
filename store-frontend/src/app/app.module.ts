import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClientListComponent } from './components/client-list/client-list.component';
import { ClientAddComponent } from './components/client-add/client-add.component';
import { ClientEditComponent } from './components/client-edit/client-edit.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { bootstrapApplication } from '@angular/platform-browser';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { VenteComponent } from './components/vente/vente.component';
import { AddEditVenteComponent } from './components/add-edit-vente/add-edit-vente.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatMenuModule} from "@angular/material/menu";
import {MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardModule} from "@angular/material/card";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatToolbarModule} from "@angular/material/toolbar";
import { ProduitComponent } from './components/produit/produit.component';
import { AddProduitComponent } from './components/add-produit/add-produit.component';
import { EditProduitComponent } from './components/edit-produit/edit-produit.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { StatisticComponent } from './components/statistic/statistic.component';
import { InvoiceComponent } from './components/invoice/invoice.component';



@NgModule({
  declarations: [
    AppComponent,  // Ajoutez AppComponent ici pour qu'il soit déclaré dans le module
    ClientListComponent,
    ClientAddComponent,
    ClientEditComponent,
    RegisterComponent,
    LoginComponent,
    VenteComponent,
    AddEditVenteComponent,
    NavbarComponent,
    ProduitComponent,
    AddProduitComponent,
    EditProduitComponent,
    StatisticComponent,
    InvoiceComponent,


  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatSidenavModule,
    MatCardModule,
    MatFormFieldModule,
    MatCardActions,
    MatInput,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    NgxChartsModule
  ],
  providers: [

  ],
  bootstrap: [AppComponent]  // Utilisez 'bootstrap' ici
})
export class AppModule { }
