import { Component } from '@angular/core';
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Header } from "../header/header";


@Component({
  selector: 'app-register',
  imports: [FormsModule, Header],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {

  user = {username: '', email:'', password:''};
  repeat: string = '';
  error: boolean = false;
  errorMessage: string ='';

  constructor(private auth: Auth, private router: Router){}

  onRegister(){
    if (!this.user.username.trim() || !this.user.email.trim() || !this.user.password.trim() || !this.repeat) {
      this.error = true;
      this.errorMessage = '¡No puedes dejar campos vacíos! El helado necesita datos reales.';
    return;
  }

    if(this.user.password != this.repeat){
      this.error = true;
      this.errorMessage = 'Las contraseñas no coinciden';
      return;
    }
    this.auth.register(this.user).subscribe({
      next:(res) => {
        localStorage.setItem('token', res.jwtToken);
        this.router.navigate(['/dashboard']);
        
      },
      error:(err)=>{
        this.error = true;
        this.errorMessage = err.error || 'Error desconocido';
      } 
       
    });
  }
 

};

