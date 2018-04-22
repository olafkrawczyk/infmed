import { Component, OnInit, Input } from '@angular/core';
import { Examination } from '../../models/examination';

@Component({
  selector: 'app-pulse',
  templateUrl: './pulse.component.html',
  styleUrls: ['./pulse.component.css']
})
export class PulseComponent implements OnInit {

  @Input()
  examination: Examination = {date: new Date(), value: 68, raw_data : []};

  constructor() { }

  ngOnInit() {
  }

}
