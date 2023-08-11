import { Component, OnInit, ViewChild } from '@angular/core';
import { ApiService } from '../servicio/api.servicio'; // Importa el servicio
import { Medicamento } from '../dto/medicamento.dto'; // Importa el modelo
import { Table } from 'primeng/table';
import { MessageService } from 'primeng/api';


@Component({
  selector: 'app-medicamentos',
  templateUrl: './medicamentos.component.html',
  styleUrls: ['./medicamentos.component.css']
})
export class MedicamentosComponent implements OnInit {
  @ViewChild('dt') dt!: Table;
  products: Medicamento[] = [];
  searchTerm: string = '';
  selectedMedicamento: Medicamento | null = null;
  productDialog: boolean = false;
  sellDialog: boolean = false;
  newMedicamento: Medicamento = {
    id: 0,
    nombre: '',
    laboratorio: '',
    fechaFabricacion: '',
    fechaVencimiento: '',
    cantidad: 0,
    valorUnitario: 0
  };
  cantidad: number = 0;
  precioUnitario: number = 0;
  total: number = 0;
  submitted = false;

  constructor(private apiService: ApiService, private messageService: MessageService) { } // Usa el servicio ApiService

  ngOnInit(): void {
    this.fetchMedicamentos();
  }

  fetchMedicamentos(): void {
    this.apiService.getAllMedicamentos().subscribe(data => {
      this.products = data;
    });
  }

  // Métodos para mostrar y ocultar el diálogo
  showProductDialog(): void {
    this.productDialog = true;
  }

  hideProductDialog(): void {
    this.productDialog = false;
    this.newMedicamento = {
      id: 0,
      nombre: '',
      laboratorio: '',
      fechaFabricacion: '',
      fechaVencimiento: '',
      cantidad: 0,
      valorUnitario: 0
    };
  }

  createMedicamento(): void {
    this.submitted = true;
    this.apiService.createMedicamento(this.newMedicamento).subscribe(() => {
      this.fetchMedicamentos();
      this.hideProductDialog(); // Oculta el diálogo después de crear
    });
  }

  editMedicament(product: Medicamento): void {
    this.selectedMedicamento = { ...product };
    this.newMedicamento = { ...product };
    this.showProductDialog();
  }

  deleteMedicament(product: Medicamento): void {
    if (confirm('¿Estás seguro de que deseas eliminar este medicamento?')) {
      this.apiService.deleteMedicamento(product.id).subscribe(() => {
        this.fetchMedicamentos(); // Recarga la lista después de eliminar
      });
    }
  }

  updateMedicamento(): void {
    this.submitted = true;
    this.apiService.updateMedicamento(this.newMedicamento).subscribe(() => {
      this.fetchMedicamentos();
      this.hideProductDialog(); // Oculta el diálogo después de editar
      this.selectedMedicamento = null; // Limpia la selección
    });
  }

  sellMedicament(product: Medicamento): void {
    this.selectedMedicamento = product;
    this.cantidad = 0;
    this.precioUnitario = 0;
    this.total = 0;
    this.sellDialog = true;
  }

  calcularTotal(): void {
    this.apiService.getPrecioUnitario(this.selectedMedicamento!.id, this.cantidad).subscribe(
      (total: number) => {
        this.total = total;
      },
      (error) => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error al obtener el precio unitario.' });
      }
    );
  }

  hideSellDialog(): void {
    this.sellDialog = false;
    
  }

  realizarVenta(product: Medicamento): void {
    if (this.cantidad <= 0) {
      this.messageService.add({ severity: 'warn', summary: 'Advertencia', detail: 'La cantidad debe ser mayor a 0.' });
      return;
    }

    if (confirm(`¿Estás seguro de realizar la venta de ${this.cantidad} unidades del medicamento ${product.nombre}?`)) {
      this.apiService.hacerVenta(product.id, this.cantidad).subscribe(
        () => {
          this.sellDialog = false;
          this.messageService.add({ severity: 'success', summary: 'Éxito', detail: 'Venta realizada exitosamente.' });
          this.fetchMedicamentos(); // Actualiza la lista de medicamentos después de la venta
        },
        (error) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error al realizar la venta.' });
        }
      );
    }
  }

  

}
