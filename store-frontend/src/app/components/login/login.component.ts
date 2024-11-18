import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  user = { username: '', password: '' };
  loginError: string | null = null; // Declare loginError property

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.user).subscribe(
      token => {
        if (typeof token === "string") {
          localStorage.setItem('token', token);
        } // Store JWT token
        this.loginError = null; // Clear any previous error
        this.router.navigate(['/navbar']); // Redirect to home or another page

      },
      error => {
        this.loginError = error.error || 'Invalid credentials'; // Set error message
      }
    );
  }
}
