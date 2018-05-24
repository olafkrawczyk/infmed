import { Injectable } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { Examination } from '../models/examination';
import { Subject } from 'rxjs';
import { SpinnerService } from '../services/spinner.service';

@Injectable()
export class ExaminationService {

    examinations: Subject<any>;


    constructor(private patientService: PatientService, private spinnerService : SpinnerService) {
        this.examinations = new Subject<Examination[]>();
    }

    async getExaminations(startDate: String = null, endDate: String = null, page = 0) {
        this.spinnerService.show();
        let tempExaminations = await this._getTemperatures(startDate, endDate, page, 10);
        let heartRateExaminations = await this._getHeartRates(startDate, endDate, page, 10);

        let examinations = tempExaminations;
        examinations.pages = Math.max(tempExaminations.pages, heartRateExaminations.pages);
        examinations.content = examinations.content.concat(heartRateExaminations.content);
        
        this.examinations.next(this.sortExaminations(examinations));
    }

    async getTemperatureExaminations(startDate: String = null, endDate: String = null, page = 0) {
        this.spinnerService.show();
        let examinations = await this._getTemperatures(startDate, endDate, page);
        this.examinations.next(this.sortExaminations(examinations));
    }

    async getHeartRateExaminations(startDate: String = null, endDate: String = null, page = 0) {
        this.spinnerService.show();
        let examinations = await this._getHeartRates(startDate, endDate, page);
        this.examinations.next(this.sortExaminations(examinations));
    }

    private async _getTemperatures(startDate: String = null, endDate: String = null, page, records = 20) {
        let examinations;
        if (startDate !== null && endDate !== null) {
            examinations = await <any>this.patientService
                .getTemperatureExaminations(startDate, endDate, page).toPromise();
        } else {
            examinations = await <any>this.patientService
                .getTemperatureExaminations(null, null, page, records).toPromise();
        }
        return examinations;
    }

    private async _getHeartRates(startDate: String = null, endDate: String = null, page, records = 20) {
        let examinations;

        if (startDate !== null && endDate !== null) {
            examinations = await <any>this.patientService
                .getHeartRateExaminations(startDate, endDate, page).toPromise();
        } else {
            examinations = await <any>this.patientService
                .getHeartRateExaminations(null, null, page, records).toPromise();
        }
        return examinations;
    }

    private sortExaminations(examinations: any) {
        examinations.content = examinations.content.sort((a: Examination, b: Examination) => {
            return new Date(b.date).getTime() - new Date(a.date).getTime();
        });
        return examinations;
    }

}