import { API_URL, AuthService } from '../auth/authentication.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class PatientService {
    constructor(private http : HttpClient, private authService : AuthService) {}

    registerPatient(patientData) {
        return this.http.post(API_URL+'/user/register/patient', patientData);
    }

    getTemperatureExaminations() {
        return this.http.get(`${API_URL}/patient/examination/${this.authService.username}/temperature`);
    }
}