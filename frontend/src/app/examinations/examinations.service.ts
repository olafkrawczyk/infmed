import { Injectable } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { Examination } from '../models/examination';
import { Subject } from 'rxjs';

@Injectable()
export class ExaminationService {

    examinations: Subject<any>;


    constructor(private patientService: PatientService) {
        this.examinations = new Subject<Examination[]>();
    }

    async getExaminations(startDate: String = null, endDate: String = null) {
        let tempExaminations = await this._getTemperatures(startDate, endDate);
        let heartRateExaminations = await this._getHeartRates(startDate, endDate);

        let examinations = tempExaminations;
        examinations.pages = Math.max(tempExaminations.pages, heartRateExaminations.pages);
        examinations.content = examinations.content.concat(heartRateExaminations.content);
        
        this.examinations.next(this.sortExaminations(examinations));
    }

    async getTemperatureExaminations(startDate: String = null, endDate: String = null) {
        let examinations = await this._getTemperatures(startDate, endDate);
        this.examinations.next(this.sortExaminations(examinations));
    }

    async getHeartRateExaminations(startDate: String = null, endDate: String = null) {
        let examinations = await this._getHeartRates(startDate, endDate);
        this.examinations.next(this.sortExaminations(examinations));
    }

    private async _getTemperatures(startDate: String = null, endDate: String = null) {
        let examinations;
        if (startDate !== null && endDate !== null) {
            examinations = await <any>this.patientService
                .getTemperatureExaminations(startDate, endDate).toPromise();
        } else {
            examinations = await <any>this.patientService
                .getTemperatureExaminations().toPromise();
        }
        return examinations;
    }

    private async _getHeartRates(startDate: String = null, endDate: String = null) {
        let examinations;

        if (startDate !== null && endDate !== null) {
            examinations = await <any>this.patientService
                .getHeartRateExaminations(startDate, endDate).toPromise();
        } else {
            examinations = await <any>this.patientService
                .getHeartRateExaminations().toPromise();
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