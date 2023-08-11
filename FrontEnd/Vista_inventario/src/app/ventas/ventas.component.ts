import { Component } from '@angular/core';
import { ApiService } from '../servicio/api.servicio';
import { Venta } from '../dto/venta.dto';

@Component({
  selector: 'app-ventas',
  templateUrl: './ventas.component.html',
  styleUrls: ['./ventas.component.css']
})
export class VentasComponent {

  inventario: Venta[] = [];
  searchTerm: string = '';
  fechaFrom: Date | null = null;
  fechaTo: Date | null = null;  
  filterDialog: boolean = false;
  constructor(private apiService: ApiService) { } 

  ngOnInit(): void {
    this.fetchVentas();
  }
  
  showFilterDialog(): void {
    this.filterDialog = true;
  }

  fetchVentas(): void {
    this.apiService.getAllVentas().subscribe(data => {
      this.inventario = data;
    });
  }

  hidefilterDialog(): void {
    this.filterDialog = false;
    
  }

  filtrarVentasPorFechas(): void {
    if (this.fechaFrom && this.fechaTo) {
      const formattedFechaFrom = this.formatDate(this.fechaFrom);
      const formattedFechaTo = this.formatDate(this.fechaTo);
  
      this.apiService.getVentasByFecha(formattedFechaFrom, formattedFechaTo).subscribe(
        (ventas: Venta[]) => {
          this.inventario = ventas;
          this.filterDialog = false;
        },
        (error) => {
          console.error('Error al obtener las ventas filtradas por fechas:', error);
        }
      );
    } else {
      console.warn('Las fechas de inicio y fin deben estar completas para filtrar.');
    }
  }


  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const day = (date.getMonth() + 1).toString().padStart(2, '0');
    const month = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  limpiarFiltro(): void {
    this.fetchVentas();
  }

}
