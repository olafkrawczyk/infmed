import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators'
import { User } from '../models/user';

import * as JWT from 'jwt-decode';

export const API_URL = 'http://192.168.0.14:8080';
const token_name = 'infmed_token';

@Injectable()
export class AuthService {

    private loggedUsername: string;
    private _isAuthenticated: boolean = false;
    private _token: string;
    private _role : string;

    constructor(private http: HttpClient) {
        const token = localStorage.getItem(token_name);
        if( token != null && !JWT(token).expired) {
            console.log(JWT(token).expired);
            this.authenticate(token);
        }
    }

    get username() : string {
        return this.loggedUsername;
    }

    private authenticate(token) : void {
        this.saveToken(token);
        this.saveUserData(token)
        this._isAuthenticated = true;
    }

    private saveToken(token): void {
        this._token = token;
        localStorage.setItem(token_name, token);
    }

    private saveUserData(token) : void {
        const decodedJWT = JWT(token);
        this.loggedUsername = decodedJWT.sub;
        this._role = decodedJWT.role;
    }

    login(username, password): Observable<HttpResponse<any>> {
        return this.http.post<any>(API_URL + '/login',
            { username: username, password: password }, { observe: 'response' })
            .pipe(tap(
                (data: HttpResponse<any>) => {
                    const token = data.headers.get('authorization');
                    this.authenticate(token);
                }
            ));
    }

    logout(): void {
        this._isAuthenticated = false;
        localStorage.removeItem(token_name);
    }

    isAuthenticated(): boolean {
        return this._isAuthenticated;
    }

    getToken() : string {
        return this._token;
    }

    getRole() : string {
        return this._role;
    }

}