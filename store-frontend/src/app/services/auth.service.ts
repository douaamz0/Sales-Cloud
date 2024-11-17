import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {jwtDecode} from "jwt-decode";
import {Router} from "@angular/router";


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8085/auth'; // Base URL for your backend API
  private jwtToken: string | null = null; // Store JWT token

  constructor(private http: HttpClient, private router:Router) {}

  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user).pipe(
      catchError((error) => {
        console.error('Registration error:', error);
        return throwError(() => new Error(error.error?.message || 'Registration failed'));
      })
    );
  }

  login(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, user, { responseType: 'text' as 'json' }).pipe(
      catchError((error) => {
        console.error('Login error:', error);
        return throwError(() => new Error(error.error?.message || 'Login failed'));
      })
    );
  }

  setToken(token: string): void {
    this.jwtToken = token;
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return this.jwtToken || localStorage.getItem('token');
  }

  logout(): void {
    this.jwtToken = null;
    localStorage.removeItem('token');
    console.log('User logged out successfully!');
    this.router.navigate(['/login']); // Redirige vers la page de connexion
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    return !!token;
  }



  // Méthode isAuthenticated pour vérifier l'authentification de l'utilisateur
  isAuthenticated(): boolean {
    const token = this.getToken();
    console.log("le token est:",token)
    if (!token) {
      return false;
    }

    try {
      const decodedToken: any = jwtDecode(token);
      const currentTime = Math.floor(Date.now() / 1000); // En secondes
      return decodedToken.exp > currentTime; // Vérifie que le token n'est pas expiré
    } catch (error) {
      console.error('Invalid token:', error);
      return false;
    }
  }


}
