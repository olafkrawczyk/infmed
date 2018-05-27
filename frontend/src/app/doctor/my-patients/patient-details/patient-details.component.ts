import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from '../../../models/user';
import { DoctorService } from '../../../services/doctor.service';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.css']
})
export class PatientDetailsComponent implements OnInit, OnDestroy {

  private sub : Subscription
  patient : User;
  patientUsername : string;

  constructor(private activatedRoute : ActivatedRoute, private doctorService : DoctorService) { }

  ngOnInit() {
    this.sub = this.activatedRoute.params.subscribe(
      params => {
        this.patientUsername = params['username'];
        console.log(this.patientUsername);
        this.getPatientDetails(this.patientUsername);
      }
    );
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  getPatientDetails(username:string) {
    this.doctorService.getPatientDetails(username).subscribe(
      (patient : User) => this.patient = patient 
    );
  }

}
