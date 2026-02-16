import { Component } from '@angular/core';
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Header } from "../header/header";

@Component({
  selector: 'app-login',
  imports: [FormsModule, Header],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  user = {email:'', password:''};
  error: boolean = false;
  errorMessage: string = '';

  constructor(private auth: Auth, private router: Router){}

  onLogin(){
        this.auth.login(this.user).subscribe({
      next:(res) => {
        localStorage.setItem('token', res.jwtToken);
        this.router.navigate(['/dashboard']);
      },
      error:(err)=>{
        this.error = true;
        this.errorMessage = 'Creedenciales erroneas';
      }
    })
  }


}
