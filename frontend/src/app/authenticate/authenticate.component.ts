import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../services/authentication.service';

@Component({
  selector: 'app-authenticate',
  templateUrl: './authenticate.component.html',
  styleUrls: ['./authenticate.component.css']
})
export class AuthenticateComponent implements OnInit {

  private loginForm : FormGroup;
  private respMessage : string;

  constructor(private authService : AuthService) { }

  ngOnInit() {
    this.createForm();
  }

  private createForm() {
    this.loginForm = new FormGroup(
      {
        username : new FormControl(null, Validators.required),
        password : new FormControl(null, Validators.required)
      }
    );
  }

  onSubmit() {
    this.authService.login(this.loginForm.controls.username.value,
      this.loginForm.controls.password.value)
      .subscribe(data => {
        this.respMessage = "Successfully logged in" 
      },
        error => this.respMessage = 'Could not sign in. Please check your username and password');
  }

}
