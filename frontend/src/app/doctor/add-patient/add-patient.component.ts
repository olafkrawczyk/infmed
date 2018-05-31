import { Component, OnInit } from '@angular/core';
import { DoctorService } from '../../services/doctor.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from '../../models/user';
import { SpinnerService } from '../../services/spinner.service';
import { AuthService } from '../../auth/authentication.service';

@Component({
  selector: 'app-add-patient',
  templateUrl: './add-patient.component.html',
  styleUrls: ['./add-patient.component.css']
})
export class AddPatientComponent implements OnInit {

  patientSearchForm : FormGroup;
  errorMessage = '';
  successMessage = '';
  patient : User = null;
  doctor : User = null;

  constructor(private authService : AuthService, private doctorService : DoctorService, private spinnerService : SpinnerService) { }

  ngOnInit() {
    this.patientSearchForm = new FormGroup({
      pesel : new FormControl(null, [Validators.required, Validators.minLength(11), Validators.maxLength(11)])
    });
    this.authService.getAuthenticatedUserData().subscribe(
      (data : User) => {
        this.spinnerService.hide();
        this.doctor = data;
      }
    );
  }

  findPatient() {
    this.spinnerService.show();
    this.doctorService.findPatientByPesel(this.patientSearchForm.value.pesel).subscribe(
      (data : User) => {
        this.errorMessage = '';
        this.successMessage = '';
        this.patient = data;
        this.spinnerService.hide();
      },
      error => {
        this.errorMessage = error.error.message;
        this.successMessage = '';        
        this.patient = null;
        this.spinnerService.hide();        
      }
    );
  }

  addPatient(uuid) {
    this.doctorService.addPatient(this.doctor.uuid, uuid).subscribe(
      (data:any) => {
        this.successMessage = data.message;
      },
      error => this.errorMessage = error.error.message
    );
  }

}
