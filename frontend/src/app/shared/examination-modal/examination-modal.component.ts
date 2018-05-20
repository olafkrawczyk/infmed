import { Component, OnInit } from '@angular/core';
import { SimpleModalComponent } from 'ngx-simple-modal';
import { Examination } from '../../models/examination';

@Component({
  selector: 'app-examination-modal',
  templateUrl: './examination-modal.component.html',
  styleUrls: ['./examination-modal.component.css']
})
export class ExaminationModalComponent extends SimpleModalComponent<Examination, boolean> implements OnInit {

  date: Date;
  value: number;
  raw_data: number[];

  constructor() {
    super();
  }

  ngOnInit() {
  }

  confirm() {
    this.result = true;
    this.close();
  }
}
