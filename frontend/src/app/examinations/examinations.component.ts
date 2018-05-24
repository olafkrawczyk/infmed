import { Component, OnInit, OnDestroy } from '@angular/core';
import { Examination } from '../models/examination';
import { Subscription } from 'rxjs';
import { ExaminationService } from './examinations.service';
import { SpinnerService } from '../services/spinner.service';

@Component({
  selector: 'app-examinations',
  templateUrl: './examinations.component.html',
  styleUrls: ['./examinations.component.css']
})
export class ExaminationsComponent implements OnInit, OnDestroy {

  examinations: Examination[];
  examinationPage : any;
  subscription: Subscription;

  constructor(private examinationService: ExaminationService, private spinnerService : SpinnerService) { }

  ngOnInit() {
    this.subscription = this.examinationService.examinations.subscribe(
      (data : any) => {
        this.spinnerService.hide();
        this.examinations = data.content;
        this.examinationPage = data;
      });
    this.examinationService.getExaminations();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
