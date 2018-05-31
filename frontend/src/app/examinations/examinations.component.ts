import { Component, OnInit, OnDestroy } from '@angular/core';
import { Examination } from '../models/examination';
import { Subscription } from 'rxjs';
import { ExaminationService } from './examinations.service';
import { SpinnerService } from '../services/spinner.service';
import { AuthService } from '../auth/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-examinations',
  templateUrl: './examinations.component.html',
  styleUrls: ['./examinations.component.css']
})
export class ExaminationsComponent implements OnInit, OnDestroy {

  examinations: Examination[];
  examinationPage: any;
  subscription: Subscription;
  routeSub: Subscription;
  username: string;

  constructor(private route: ActivatedRoute,
    private authService: AuthService,
    private examinationService: ExaminationService,
    private spinnerService: SpinnerService,
    private router : Router) { }

  ngOnInit() {
    this.subscription = this.examinationService.examinations.subscribe(
      (data: any) => {
        this.spinnerService.hide();
        this.examinations = data.content;
        this.examinationPage = data;
      });
    this.routeSub = this.route.params.subscribe(
      (params) => {
        if(this.authService.getRole() === 'ROLE_DOCTOR' && !params['username']){
          this.router.navigate(['/mypatients']);
        }
        if (this.authService.getRole() !== 'ROLE_DOCTOR') {
          this.username = this.authService.username;
          this.router.navigate(['/examinations']);
        } else {
          this.username = params['username'];
        }
        this.examinationService.getExaminations(this.username);
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.routeSub.unsubscribe();
  }
}
