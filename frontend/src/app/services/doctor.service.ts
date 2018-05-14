import { API_URL } from '../auth/authentication.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class DoctorService {
    constructor(private http : HttpClient) {}

    registerDoctor(doctorData) {
        return this.http.post(API_URL+'/user/register/doctor', doctorData);
    }
}