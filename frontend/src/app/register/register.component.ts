import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;

  constructor() {
    this.createForm();
  }

  ngOnInit() {
  }

  createForm() {
    this.registerForm = new FormGroup({
      username: new FormControl(null, Validators.required),
      name: new FormControl(null, Validators.required),
      surname: new FormControl(null, Validators.required),
      phoneNumber: new FormControl(null, Validators.required),
      pesel: new FormControl(null, Validators.required),
      birthDate: new FormControl(null, Validators.required),
      emailAddress: new FormControl(null, Validators.required),
      password: new FormControl(null, Validators.required),
      matchingPassword: new FormControl(null, Validators.required),
      accountType : new FormControl("patient"),
      address: new FormGroup(
        {
          postalCode: new FormControl(null, Validators.required),
          houseNumber: new FormControl(null, Validators.required),
          apartmentNumber: new FormControl(),
          street: new FormControl(null, Validators.required),
          city: new FormControl(null, Validators.required),
        }
      )
    });
  }

  onSubmit() {
    console.log(this.registerForm);
    console.log("elo");
  }

}
