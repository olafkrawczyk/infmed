import { Component, OnInit, Input } from '@angular/core';
import { User } from '../../../models/user';

@Component({
  selector: 'app-patient-item',
  templateUrl: './patient-item.component.html',
  styleUrls: ['./patient-item.component.css']
})
export class PatientItemComponent implements OnInit {

  @Input()patient : User;

  constructor() { }

  ngOnInit() {
  }

}
