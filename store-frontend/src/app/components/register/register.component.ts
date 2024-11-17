import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user = { username: '', password: '' };
  registrationError: string | null = null;
  registrationSuccess: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  register(): void {
    this.authService.register(this.user).subscribe(
      response => {
        this.registrationSuccess = response.message;
        console.log(this.registrationSuccess);
        this.registrationError = null;

        // Rediriger vers la page de connexion après un enregistrement réussi
        alert('Enregistrement réussi ! Redirection vers la page de connexion.');
        this.router.navigate(['/login']);
      },
      error => {
        this.registrationError = error.error.message || 'Registration failed. Please try again.';
        console.error(this.registrationError);
      }
    );
  }
}
