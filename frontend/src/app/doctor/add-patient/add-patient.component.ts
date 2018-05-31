import { Component, OnInit } from '@angular/core';
import { DoctorService } from '../../services/doctor.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-patient',
  templateUrl: './add-patient.component.html',
  styleUrls: ['./add-patient.component.css']
})
export class AddPatientComponent implements OnInit {

  patientSearchForm : FormGroup;
  errorMessage = '';

  constructor(private doctorService : DoctorService) { }

  ngOnInit() {
    this.patientSearchForm = new FormGroup({
      pesel : new FormControl(null, [Validators.required, Validators.minLength(11), Validators.maxLength(11)])
    });
  }

  findPatient() {
    this.doctorService.findPatientByPesel(this.patientSearchForm.value.pesel).subscribe(
      (data) => {
        console.log(data);
        this.errorMessage = '';
      },
      error => this.errorMessage = error.error.message
    );
  }

}
