import { Component, OnInit } from '@angular/core';
import { DoctorService } from '../../services/doctor.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from '../../models/user';
import { SpinnerService } from '../../services/spinner.service';

@Component({
  selector: 'app-add-patient',
  templateUrl: './add-patient.component.html',
  styleUrls: ['./add-patient.component.css']
})
export class AddPatientComponent implements OnInit {

  patientSearchForm : FormGroup;
  errorMessage = '';
  patient : User = null;

  constructor(private doctorService : DoctorService, private spinnerService : SpinnerService) { }

  ngOnInit() {
    this.patientSearchForm = new FormGroup({
      pesel : new FormControl(null, [Validators.required, Validators.minLength(11), Validators.maxLength(11)])
    });
  }

  findPatient() {
    this.spinnerService.show();
    this.doctorService.findPatientByPesel(this.patientSearchForm.value.pesel).subscribe(
      (data : User) => {
        this.errorMessage = '';
        this.patient = data;
        this.spinnerService.hide();
      },
      error => {
        this.errorMessage = error.error.message;
        this.patient = null;
        this.spinnerService.hide();        
      }
    );
  }

}
