import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { PatientService } from '../services/patient.service';
import { Subject } from 'rxjs';

@Injectable()
export class MyDoctorsService {
    public doctors : Subject<any[]>;

    constructor(private patientService: PatientService) {
        this.doctors = new Subject<any[]>();
    }

    public async getDoctors() {
        let docs = await <any>this.patientService.getDoctors().toPromise();
        this.doctors.next(docs);
    }
}