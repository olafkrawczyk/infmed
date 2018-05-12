import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../models/user';

export const API_URL = 'http://localhost:8080';

@Injectable()
export class AuthService {
    
    private loggedUser : User;
    private isAuthenticated : boolean;

    constructor(private http: HttpClient) {

    }

    loginIn(username, password) {
        return null;
    }

    logOut() {
        return null;
    }
}