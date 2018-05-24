import { Component, OnInit } from '@angular/core';
import { SimpleModalComponent } from 'ngx-simple-modal';
import { Examination } from '../../models/examination';
import * as Chartist from 'chartist';


const SCALING_FACTOR = 6;

@Component({
  selector: 'app-examination-modal',
  templateUrl: './examination-modal.component.html',
  styleUrls: ['./examination-modal.component.css']
})
export class ExaminationModalComponent extends SimpleModalComponent<Examination, boolean> implements OnInit {

  date: Date;
  value: number;
  rawData: number[];

  constructor() {
    super();
  }

  ngOnInit() {

    var dataExam = {
      labels: new Array(this.rawData.length),
      series: [[...this.rawData]]
    };

    var examOpt = {
        seriesBarDistance: 1,
        axisX: {
            showGrid: false
        },
        height: "245px",
        width: `${this.rawData.length*SCALING_FACTOR}px`,
        showPoint: false,
    };

    var respExamOpt: any[] = [
      ['screen and (max-width: 640px)', {
        seriesBarDistance: 1,
        axisX: {
          labelInterpolationFnc: function (value) {
            return value[0];
          }
        }
      }]
    ];

    new Chartist.Line('#examChart', dataExam, examOpt, respExamOpt);
  }

  confirm() {
    this.result = true;
    this.close();
  }
}

