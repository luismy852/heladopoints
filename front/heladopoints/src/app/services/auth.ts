import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private API_URL = environment.apiUrl;

  constructor(private http: HttpClient) {}

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.API_URL}/login`, credentials);
  }

  register(userData: any): Observable<any> {
    return this.http.post(`${this.API_URL}/register`, userData);
  }

  user(): Observable<any>{

    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });
    return this.http.get(`${this.API_URL}/user`, {headers});

  }

  history(): Observable<any>{

    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });
    return this.http.get(`${this.API_URL}/scan`, {headers});

  }

  uploadScan(formData: FormData): Observable<any>{
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });
    return this.http.post(`${this.API_URL}/scan`, formData, { headers });

  }


}
