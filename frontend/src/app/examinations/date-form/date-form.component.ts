import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-date-form',
  templateUrl: './date-form.component.html',
  styleUrls: ['./date-form.component.css']
})
export class DateFormComponent implements OnInit {

  filterForm : FormGroup;

  constructor() { }

  ngOnInit() {
    this.filterForm = new FormGroup({});
  }

}
