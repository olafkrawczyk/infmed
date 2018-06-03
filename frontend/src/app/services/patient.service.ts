import { API_URL, AuthService } from '../auth/authentication.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';


@Injectable()
export class PatientService {
    constructor(private http: HttpClient, private authService: AuthService) { }

    registerPatient(patientData) {
        return this.http.post(API_URL + '/user/register/patient', patientData);
    }

    getTemperatureExaminations(username, startDate = null, endDate = null, page = 0, records = 20) {
        if (startDate !== null && endDate !== null) {
            return this.http.get(`${API_URL}/patient/examination/${username}/temperature?startDate=${startDate}&endDate=${endDate}&page=${page}&size=${records}&sort=date,desc`);
        }
        return this.http.get(`${API_URL}/patient/examination/${username}/temperature?&page=${page}&size=${records}&sort=date,desc`);
    }
    getHeartRateExaminations(username, startDate = null, endDate = null, page = 0, records = 20) {
        if (startDate !== null && endDate !== null) {
            return this.http.get(`${API_URL}/patient/examination/${username}/heart-rate?startDate=${startDate}&endDate=${endDate}&page=${page}&size=${records}&sort=date,desc`);
        }
        return this.http.get(`${API_URL}/patient/examination/${username}/heart-rate?&page=${page}&size=${records}&sort=date,desc`);
    }
    getDoctors() {
        return this.http.get(`${API_URL}/patient/${this.authService.username}/doctors`);
    }

    updateUserData(user : any){
        return this.http.post(`${API_URL}/user/update/${this.authService.username}`, user);
    }
}