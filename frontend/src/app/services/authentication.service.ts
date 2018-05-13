import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import 'rxjs/operator/map';

import { User } from '../models/user';

export const API_URL = 'http://localhost:8080';

@Injectable()
export class AuthService {
    
    private loggedUser : User;
    private isAuthenticated : boolean;

    constructor(private http: HttpClient) {

    }

    logIn(username, password) {
        return this.http.post(API_URL+'/login',
            {username : username, password: password},
            {responseType: 'text', observe: 'response'});
    }

    logOut() {
        return null;
    }
}