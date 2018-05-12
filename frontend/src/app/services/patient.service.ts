import { API_URL } from './authentication.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class UserSerivce {
    constructor(private http : HttpClient) {}

    registerPatient(patientData) {
        return this.http.post(API_URL, patientData);
    }
}