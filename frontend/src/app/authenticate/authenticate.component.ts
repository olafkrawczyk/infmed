import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-authenticate',
  templateUrl: './authenticate.component.html',
  styleUrls: ['./authenticate.component.css']
})
export class AuthenticateComponent implements OnInit {

  private loginForm : FormGroup;
  constructor() { }

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
    console.log(this.loginForm.value);
  }

}
