import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { DoctorService } from '../../services/doctor.service';

@Component({
  selector: 'app-my-patients',
  templateUrl: './my-patients.component.html',
  styleUrls: ['./my-patients.component.css']
})
export class MyPatientsComponent implements OnInit {

  private patients : User[];

  constructor(private doctorServie : DoctorService) { }

  ngOnInit() {
    this.doctorServie.getPatients().subscribe(
      (patients : User[]) => {this.patients = patients;
        console.log(this.patients)}
    )
  }

}
