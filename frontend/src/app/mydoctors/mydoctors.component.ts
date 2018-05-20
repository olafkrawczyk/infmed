import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { User } from '../models/user';
import { MyDoctorsService } from './mydoctors.service';

@Component({
  selector: 'app-mydoctors',
  templateUrl: './mydoctors.component.html',
  styleUrls: ['./mydoctors.component.css']
})
export class MyDoctorsComponent implements OnInit, OnDestroy {
    doctors: any[];
    subscription: Subscription;

    constructor(private mydoctorsService: MyDoctorsService) { }

    ngOnInit() {
        this.subscription = this.mydoctorsService.doctors.subscribe((data : any[]) => this.doctors = data);
        this.mydoctorsService.getDoctors();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
      }
}
