import { Injectable } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { Examination } from '../models/examination';
import { Subject } from 'rxjs';

@Injectable()
export class ExaminationService {

    private _examinations: Examination[];
    examinations: Subject<Examination[]>;


    constructor(private patientService: PatientService) {
        this._examinations = [];
        this.examinations = new Subject<Examination[]>();
    }

    async getExaminations(startDate: String = null, endDate: String = null) {
        let tempExaminations = await this._getTemperatures(startDate, endDate);
        let heartRateExaminations = await this._getHeartRates(startDate, endDate);

        this._examinations = tempExaminations;
        this._examinations = this._examinations.concat(heartRateExaminations);
        this.examinations.next(this.sortExaminations(this._examinations));
    }

    async getTemperatureExaminations(startDate: String = null, endDate: String = null) {
        this._examinations = await this._getTemperatures(startDate, endDate);
        this.examinations.next(this.sortExaminations(this._examinations));
    }

    async getHeartRateExaminations(startDate: String = null, endDate: String = null) {
        this._examinations = await this._getHeartRates(startDate, endDate);
        this.examinations.next(this.sortExaminations(this._examinations));
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

        console.log(examinations);
        return examinations.content;
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
        return examinations.content;
    }

    private sortExaminations(examinations: Examination[]) {
        return examinations.sort((a: Examination, b: Examination) => {
            return new Date(b.date).getTime() - new Date(a.date).getTime();
        });
    }

}