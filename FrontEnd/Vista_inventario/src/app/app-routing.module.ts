import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MedicamentosComponent } from './medicamentos/medicamentos.component';
import { VentasComponent } from './ventas/ventas.component';


const routes: Routes = [
  { path: 'medicamentos', component: MedicamentosComponent },
  { path: 'ventas', component: VentasComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
