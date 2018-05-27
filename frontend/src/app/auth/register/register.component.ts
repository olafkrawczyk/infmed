import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { PatientService } from '../../services/patient.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { DoctorService } from '../../services/doctor.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  responseMessage: string;

  constructor(private patientService: PatientService,
    private doctorService: DoctorService,
    private router: Router) {

  }

  ngOnInit() {
    this.createForm();
  }

  createForm() {
    this.registerForm = new FormGroup({
      username: new FormControl(null, Validators.required),
      name: new FormControl(null, Validators.required),
      surname: new FormControl(null, Validators.required),
      phoneNumber: new FormControl(null, Validators.required),
      pesel: new FormControl(null, [Validators.required, Validators.minLength(11), Validators.maxLength(11)]),
      birthDate: new FormControl(null, Validators.required),
      emailAddress: new FormControl(null, Validators.required),
      password: new FormControl(null, Validators.required),
      matchingPassword: new FormControl(null, Validators.required),
      accountType: new FormControl("patient"),
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
    if (this.registerForm.valid) {
      if (this.registerForm.controls.accountType.value === 'patient') {
        this.patientService.registerPatient(this.registerForm.value)
          .subscribe(
            (resp: any) => {
              this.responseMessage = resp.message;
              setTimeout(() => this.router.navigate(['/login']), 3000);
            },
            (error: HttpErrorResponse) => {
              const msg = JSON.parse(error.error);
              if (msg.errors) {
                this.responseMessage = msg.errors[0].defaultMessage;
              } else {
                this.responseMessage = msg.message;
              }
            });
      } else {
        this.doctorService.registerDoctor(this.registerForm.value)
          .subscribe(
            (resp: any) => {
              this.responseMessage = resp.message;
              setTimeout(() => this.router.navigate(['/login']), 3000);
            },
            (error: HttpErrorResponse) => {
              const msg = JSON.parse(error.error);
              if (msg.errors) {
                this.responseMessage = msg.errors[0].defaultMessage;
              } else {
                this.responseMessage = msg.message;
              }
            });
      }
    }
  }

}
