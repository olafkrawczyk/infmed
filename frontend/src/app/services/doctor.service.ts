import { API_URL, AuthService } from '../auth/authentication.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
import { SpinnerService } from './spinner.service';
import { Subject } from 'rxjs';


@Injectable()
export class DoctorService {

    patientsSubject = new Subject<any>();

    constructor(private http : HttpClient, private authService : AuthService, private spinnerService : SpinnerService) {}

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

    addPatient(doctor_uuid, patient_uuid){
        return this.http.post(`${API_URL}/doctor/patient`, {doctor_uuid : doctor_uuid, patient_uuid : patient_uuid});
    }

    removePatient(patient_uuid){
        this.authService.getAuthenticatedUserData().subscribe(
            (data : User) => {
                this.http.post(`${API_URL}/doctor/patient/delete`, {doctor_uuid : data.uuid, patient_uuid : patient_uuid})
                    .subscribe((data) => {
                        this.patientsSubject.next();
                        this.spinnerService.hide();
                    });
            }
        );  
    }
}