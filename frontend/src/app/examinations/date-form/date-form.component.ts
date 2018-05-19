import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { ExaminationService } from '../examinations.service';

@Component({
  selector: 'app-date-form',
  templateUrl: './date-form.component.html',
  styleUrls: ['./date-form.component.css']
})
export class DateFormComponent implements OnInit {

  filterForm : FormGroup;

  constructor(private examinationService : ExaminationService) { }

  ngOnInit() {
    this.filterForm = new FormGroup({
      startDate : new FormControl(null, Validators.required),
      endDate : new FormControl(null, Validators.required),
      temperature : new FormControl(false),
      heartRate : new FormControl(false)
    });
  }

  submit() {
    console.log(this.filterForm.value);
    if(this.filterForm.value.temperature && !this.filterForm.value.heartRate) {
      this.examinationService
        .getTemperatureExaminations(this.filterForm.value.startDate, this.filterForm.value.endDate);
    } else if(!this.filterForm.value.temperature && this.filterForm.value.heartRate) {
      this.examinationService
        .getHeartRateExaminations(this.filterForm.value.startDate, this.filterForm.value.endDate);
    } else {
      this.examinationService
        .getExaminations(this.filterForm.value.startDate, this.filterForm.value.endDate);
    }
  }

}
