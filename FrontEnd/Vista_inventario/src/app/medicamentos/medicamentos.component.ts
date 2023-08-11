import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-medicamentos',
  templateUrl: './medicamentos.component.html',
  styleUrls: ['./medicamentos.component.css']
})
export class MedicamentosComponent implements OnInit {
  products: any[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchMedicamentos();
  }

  fetchMedicamentos(): void {
    this.http.get<any[]>('http://localhost:8080/api/Medicamentos/all')
      .subscribe(data => {
        this.products = data;
      });
  }
}
