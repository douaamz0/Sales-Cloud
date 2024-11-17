import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth'; // Base URL for your backend API
  private jwtToken: string | null = null; // Store JWT token

  constructor(private http: HttpClient) {}

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
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    return !!token;
  }

  // Méthode isAuthenticated pour vérifier l'authentification de l'utilisateur
  isAuthenticated(): boolean {
    const token = this.getToken();
    if (!token) {
      return false;
    }
    // Ajoutez ici des vérifications supplémentaires si nécessaire, par exemple sur l'expiration du token.
    // Pour des vérifications avancées, vous pourriez décoder le token et vérifier sa validité
    return true;
  }
}
