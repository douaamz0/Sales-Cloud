import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit{

  constructor(private router:Router, private authService:AuthService) {
  }
  identifier() {
    this.router.navigateByUrl("/login")
  }

  ngOnInit(): void {
  }
  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  viewProfile() {

  }

  logout() {
    this.authService.logout();
  }
}
