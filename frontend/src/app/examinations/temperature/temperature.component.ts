import { Component, OnInit, Input } from '@angular/core';
import { Examination } from '../../models/examination';

@Component({
  selector: 'app-temperature',
  templateUrl: './temperature.component.html',
  styleUrls: ['./temperature.component.css']
})
export class TemperatureComponent implements OnInit {

  @Input()
  examination: Examination = {date: new Date(), value: 36.6, rawData : []};

  constructor() { }

  ngOnInit() {
  }

}
