import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { DoctorService } from '../../services/doctor.service';

@Component({
  selector: 'app-my-patients',
  templateUrl: './my-patients.component.html',
  styleUrls: ['./my-patients.component.css']
})
export class MyPatientsComponent implements OnInit {

  patients : User[];

  constructor(private doctorService : DoctorService) { }

  ngOnInit() {
    this.doctorService.getPatients().subscribe(
      (patients : User[]) => this.patients = patients
    )
  }

}
