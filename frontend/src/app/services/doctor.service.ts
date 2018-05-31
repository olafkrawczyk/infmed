import { API_URL, AuthService } from '../auth/authentication.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';


@Injectable()
export class DoctorService {
    constructor(private http : HttpClient, private authService : AuthService) {}

    registerDoctor(doctorData) {
        return this.http.post(API_URL+'/user/register/doctor', doctorData);
    }

    getPatients(){
        return this.http.get(`${API_URL}/doctor/patients`);
    }

    getPatientDetails(username : string){
        return this.http.get(`${API_URL}/doctor/patients/${username}`);
    }

    findPatientByPesel(pesel) {
        return this.http.get(`${API_URL}/doctor/patients/findByPESEL/${pesel}`);
    }
}