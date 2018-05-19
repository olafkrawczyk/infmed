import { API_URL, AuthService } from '../auth/authentication.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class PatientService {
    constructor(private http: HttpClient, private authService: AuthService) { }

    registerPatient(patientData) {
        return this.http.post(API_URL + '/user/register/patient', patientData);
    }

    getTemperatureExaminations(startDate = null, endDate = null, page = 0, records = 20) {
        if (startDate !== null && endDate !== null) {
            return this.http.get(`${API_URL}/patient/examination/${this.authService.username}/temperature?startDate=${startDate}&endDate=${endDate}&page=${page}&size=${records}`);
        }
        return this.http.get(`${API_URL}/patient/examination/${this.authService.username}/temperature?&page=${page}&size=${records}`);
    }
    getHeartRateExaminations(startDate = null, endDate = null, page = 0, records = 20) {
        if (startDate !== null && endDate !== null) {
            return this.http.get(`${API_URL}/patient/examination/${this.authService.username}/heart-rate?startDate=${startDate}&endDate=${endDate}&page=${page}&size=${records}`);
        }
        return this.http.get(`${API_URL}/patient/examination/${this.authService.username}/heart-rate?&page=${page}&size=${records}`);
    }
}