import { Component, OnInit } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { Examination } from '../models/examination';

@Component({
  selector: 'app-examinations',
  templateUrl: './examinations.component.html',
  styleUrls: ['./examinations.component.css']
})
export class ExaminationsComponent implements OnInit {

  examinations: Examination[];

  constructor(private patientService: PatientService) { }

  ngOnInit() {
    this.patientService.getTemperatureExaminations().subscribe(
      (data: any) => {
        this.examinations = data.content;
        this.patientService.getHeartRateExaminations().subscribe(
          (data: any) => {
            this.examinations = this.examinations.concat(data.content);
            this.examinations.sort((a: Examination, b: Examination) => {
              return new Date(b.date).getTime() - new Date(a.date).getTime();
            });
            console.log(this.examinations);
          },
          err => console.error(err));
      },
      error => console.log(error)
    );
  }

}
