import { Component } from '@angular/core';
import { Auth } from '../../services/auth';
import { CommonModule, DatePipe } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {

  constructor(private auth: Auth, private route: Router){}

  userInfo = {username:'', points:''};
  histories: any[] = [];
  showModal = false;
  finish = false;
  message = '';
  image = '';

  ngOnInit(){
    const token = localStorage.getItem('token');
    console.log('este es el token: ' + token);

    if(!token){
      this.route.navigate([""]);
      return;
    }

    this.auth.user().subscribe({
      next:(res)=>{
        this.userInfo.points = res.points;
        this.userInfo.username = res.username;
        console.log("carga exitosa " + res);
      },
      error:(err)=> {
        if (err.status === 403 || err.status === 401) {
      console.warn("Sesión expirada o inválida. Saliendo...");
      localStorage.removeItem('token');
      this.route.navigate(['/']);
    }
      }
    });

    this.auth.history().subscribe({
      next:(res)=>{
        this.histories = res;
        console.log("carga exitosa " + res);
      },
      error:(err)=> {
        if (err.status === 403 || err.status === 401) {
      console.warn("Sesión expirada o inválida. Saliendo...");
      localStorage.removeItem('token');
      this.route.navigate(['/']);
    }
      }
    });
  }

  onFileSelected(event: any, input: HTMLInputElement) {
  const file: File = event.target.files[0];

  if (file) {
    const formData = new FormData();
    formData.append('file', file); // 'image' debe coincidir con el @RequestParam del Back
    this.showModal = true
    // Llamamos al servicio (que crearemos abajo)
    this.auth.uploadScan(formData).subscribe({
      next: (res) => {
        this.finish = true;
        this.image= 'accept.png';
        this.message = "¡Éxito! Has ganado 100 puntos.";
        this.ngOnInit(); // Recargamos los datos para ver el nuevo registro
        input.value= '';
      },
      error: (err) => {
        this.finish = true;
        this.image = 'cross.png';
        if (err.status === 403 || err.status === 401) {
      console.warn("Sesión expirada o inválida. Saliendo...");
      localStorage.removeItem('token');
      this.route.navigate(['/']);
    }else{
       let msg = err.error?.message || err.error || '';
        input.value= '';

       if (msg.includes('No valid ice cream products were found on this receipt.')) {
          this.message = "No se encontraron helados en tu factura.";
        } else if (msg.includes('the image does not appear to be a valid official receipt.')) {
          this.message = "La imagen no parece ser una factura válida.";
        }else if(msg.includes('10MB')){
          this.message = 'La imagen es muy pesada el peso maximo son 10MB';
        } else if(msg.includes('Está factura ya fue subida')){
          this.message = msg;
        }
        else {
          this.message = "Ocurrió un error inesperado.";
        }

    }
      }
      
    });
    this.ngOnInit();
  }
}

closeSession(){
  localStorage.removeItem('token');
  this.route.navigate(['/']);
}

  closeModal(){
    this.showModal = false;
    this.finish = false;
  }
}
