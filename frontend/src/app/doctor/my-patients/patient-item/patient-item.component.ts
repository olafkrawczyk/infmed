import { Component, OnInit, Input } from '@angular/core';
import { User } from '../../../models/user';
import { Router } from '@angular/router';
import { DoctorService } from '../../../services/doctor.service';
import { AuthService } from '../../../auth/authentication.service';

@Component({
  selector: 'app-patient-item',
  templateUrl: './patient-item.component.html',
  styleUrls: ['./patient-item.component.css']
})
export class PatientItemComponent implements OnInit {

  @Input()patient : User;

  constructor(private router : Router, private doctorService : DoctorService, private authService : AuthService) { }

  ngOnInit() {
  }

  showMoreInfo(){
    this.router.navigate(['/mypatients', this.patient.username]);
  }

  deletePatient() {
    if(confirm(`Delete ${this.patient.name} ${this.patient.surname} from your patients?`)){
      this.doctorService.removePatient(this.patient.uuid);
    }
  }

}
