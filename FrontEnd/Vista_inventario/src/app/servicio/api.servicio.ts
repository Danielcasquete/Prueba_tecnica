import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Medicamento } from '../dto/medicamento.dto';
import { Venta } from '../dto/venta.dto';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8080/api';
  private baseUrl2 = 'http://localhost:8081/api';

  constructor(private http: HttpClient) { }

  getAllMedicamentos(): Observable<Medicamento[]> {
    return this.http.get<Medicamento[]>(`${this.baseUrl}/Medicamentos/all`);
  }

  createMedicamento(medicamento: Medicamento): Observable<any> {
    return this.http.post(`${this.baseUrl}/Medicamentos`, medicamento);
  }

  deleteMedicamento(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/Medicamentos/${id}`);
  }

  updateMedicamento(medicamento: Medicamento): Observable<any> {
    return this.http.put(`${this.baseUrl}/Medicamentos`, medicamento);
  }
  
  getPrecioUnitario(id: number, quantity: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrl2}/Inventario/precioUnitario/${id}?quantity=${quantity}`);
  }

  hacerVenta(idMedicamento: number, cantidad: number): Observable<any> {
    return this.http.post(`${this.baseUrl2}/Inventario/HacerVenta/${idMedicamento}/${cantidad}`, {});
  }

  getAllVentas(): Observable<Venta[]> {
    return this.http.get<Venta[]>(`${this.baseUrl2}/Inventario/all`);
  }

  getVentasByFecha(from: String, to: String): Observable<Venta[]> {
    return this.http.get<Venta[]>(`${this.baseUrl2}/Inventario/FechaVenta/${from}/${to}`);
  }

}