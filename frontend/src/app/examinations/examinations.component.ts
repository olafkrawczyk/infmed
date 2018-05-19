import { Component, OnInit, OnDestroy } from '@angular/core';
import { Examination } from '../models/examination';
import { Subscription } from 'rxjs';
import { ExaminationService } from './examinations.service';

@Component({
  selector: 'app-examinations',
  templateUrl: './examinations.component.html',
  styleUrls: ['./examinations.component.css']
})
export class ExaminationsComponent implements OnInit, OnDestroy {

  examinations: Examination[];
  examinationPage : any;
  subscription: Subscription;

  constructor(private examinationService: ExaminationService) { }

  ngOnInit() {
    this.subscription = this.examinationService.examinations.subscribe(
      (data : any) => {
        this.examinations = data.content;
        this.examinationPage = data;
      });
    this.examinationService.getExaminations();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
