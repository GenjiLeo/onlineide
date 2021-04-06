import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router:Router, public authService:AuthService) { }

  ngOnInit(): void {
  }

  // Redirect to the project list if the user is authenticated
  login() {
    if(this.authService.authenticated) {
      console.log("User already authenticated, redirecting to project page");
      this.router.navigate(['projects']);
    } else {
      console.log("Authenticating user");
      this.authService.login();
    }
  }
}
