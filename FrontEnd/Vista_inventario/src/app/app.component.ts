import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Vista_inventario';
  stateOptions: any[] = [
    { label: 'Medicamentos', value: 'medicamentos' },
    { label: 'Ventas', value: 'ventas' }
  ];

  selectedRoute: string = 'medicamentos';

  constructor(private router: Router) {}

  onRouteChange(event: any) {
    this.selectedRoute = event.value;
    this.router.navigate([`/${this.selectedRoute}`]);
  }

}
