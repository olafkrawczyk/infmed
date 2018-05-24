import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { ExaminationService } from '../examinations.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-date-form',
  templateUrl: './date-form.component.html',
  styleUrls: ['./date-form.component.css']
})
export class DateFormComponent implements OnInit, OnDestroy {

  filterForm : FormGroup;
  subscription : Subscription;
  page : number;
  totalPages : any[];

  constructor(private examinationService : ExaminationService) { }

  ngOnInit() {
    this.filterForm = new FormGroup({
      startDate : new FormControl(null, Validators.required),
      endDate : new FormControl(null, Validators.required),
      temperature : new FormControl(false),
      heartRate : new FormControl(false)
    });
    this.subscription = this.examinationService.examinations.subscribe(
      data => {
        this.page = data.number;
        this.totalPages = new Array(data.totalPages);
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  getPageResults(page = 0) {
    if(this.filterForm.value.temperature && !this.filterForm.value.heartRate) {
      this.examinationService
        .getTemperatureExaminations(this.filterForm.value.startDate, this.filterForm.value.endDate, page);
    } else if(!this.filterForm.value.temperature && this.filterForm.value.heartRate) {
      this.examinationService
        .getHeartRateExaminations(this.filterForm.value.startDate, this.filterForm.value.endDate, page);
    } else {
      this.examinationService
        .getExaminations(this.filterForm.value.startDate, this.filterForm.value.endDate, page);
    }   
  }

}
