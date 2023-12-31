import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MedicamentosComponent } from './medicamentos/medicamentos.component';
import { VentasComponent } from './ventas/ventas.component';
import { SelectButtonModule } from 'primeng/selectbutton';
import { ImageModule } from 'primeng/image';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TableModule } from 'primeng/table';
import { ToolbarModule } from 'primeng/toolbar';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { InputNumberModule } from 'primeng/inputnumber';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';

@NgModule({
  declarations: [
    AppComponent,
    MedicamentosComponent,
    VentasComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SelectButtonModule,
    ImageModule,
    BrowserAnimationsModule,
    TableModule,
    ToolbarModule,
    ToastModule,
    DialogModule,
    InputNumberModule,
    ConfirmDialogModule,
    HttpClientModule,
    FormsModule,
    InputTextModule,
    CalendarModule
  ],
  providers: [MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
