export interface Medicamento {
    id:number
    nombre: string;
    laboratorio: string;
    fechaFabricacion: Date | null;
    fechaVencimiento: Date | null;
    cantidad: number;
    valorUnitario: number;
  }