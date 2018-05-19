import { Component, OnInit } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { Examination } from '../models/examination';

@Component({
  selector: 'app-examinations',
  templateUrl: './examinations.component.html',
  styleUrls: ['./examinations.component.css']
})
export class ExaminationsComponent implements OnInit {

  examinations : Examination[];

  constructor(private patientService : PatientService) { }

  ngOnInit() {
    this.patientService.getTemperatureExaminations().subscribe(
      (data : any) => {this.examinations = data.content},
      error => console.log(error)
    );
  }

}
