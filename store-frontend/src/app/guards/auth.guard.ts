import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }

  // This function determines whether the route can be activated
  canActivate(): boolean {
    if (this.authService.isAuthenticated()) {
      return true; // Allow access to the route if the user is authenticated
    } else {
      this.router.navigate(['/login']); // Redirect to login if not authenticated
      return false;
    }
  }
}
