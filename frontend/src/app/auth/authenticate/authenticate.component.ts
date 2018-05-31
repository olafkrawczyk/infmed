import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../authentication.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-authenticate',
  templateUrl: './authenticate.component.html',
  styleUrls: ['./authenticate.component.css']
})
export class AuthenticateComponent implements OnInit {

  private loginForm : FormGroup;
  private respMessage : string;

  constructor(private authService : AuthService,
    private route : ActivatedRoute,
    private router : Router) { }

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
    const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/examinations';
    this.authService.login(this.loginForm.controls.username.value,
      this.loginForm.controls.password.value)
      .subscribe(data => {
        this.respMessage = "Successfully logged in" 
        this.router.navigate([returnUrl]);
      },
        error => this.respMessage = 'Could not sign in. Please check your username and password');
  }

}
