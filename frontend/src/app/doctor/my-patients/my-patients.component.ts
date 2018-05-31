import { Component, OnInit, OnDestroy } from '@angular/core';
import { User } from '../../models/user';
import { DoctorService } from '../../services/doctor.service';
import { Subscription } from 'rxjs';
import { SpinnerService } from '../../services/spinner.service';

@Component({
  selector: 'app-my-patients',
  templateUrl: './my-patients.component.html',
  styleUrls: ['./my-patients.component.css']
})
export class MyPatientsComponent implements OnInit, OnDestroy {

  patients : User[];
  patientsSubscription : Subscription;

  constructor(private doctorService : DoctorService, private spinnerService : SpinnerService) { }

  ngOnInit() {
    this.getPatients();
    this.patientsSubscription = this.doctorService.patientsSubject.subscribe(
      (data) => {
        this.getPatients();
      }
    );
  }

  ngOnDestroy() {
    this.patientsSubscription.unsubscribe();
  }

  private getPatients() {
    this.doctorService.getPatients().subscribe((patients: User[]) => {this.patients = patients; this.spinnerService.hide();});
  }
}
