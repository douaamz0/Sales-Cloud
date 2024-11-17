import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClientListComponent } from './components/client-list/client-list.component';
import { ClientAddComponent } from './components/client-add/client-add.component';
import { ClientEditComponent } from './components/client-edit/client-edit.component';
import { FormsModule } from '@angular/forms'; 
import { bootstrapApplication } from '@angular/platform-browser';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { VenteComponent } from './components/vente/vente.component';
import { AddEditVenteComponent } from './components/add-edit-vente/add-edit-vente.component';
 

@NgModule({
  declarations: [
    AppComponent,  // Ajoutez AppComponent ici pour qu'il soit déclaré dans le module
    ClientListComponent,
    ClientAddComponent,
    ClientEditComponent,
    RegisterComponent,
    LoginComponent,
    VenteComponent,
    AddEditVenteComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]  // Utilisez 'bootstrap' ici
})
export class AppModule { }
