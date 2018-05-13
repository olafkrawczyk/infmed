import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators'
import { User } from '../models/user';

import * as JWT from 'jwt-decode';

export const API_URL = 'http://localhost:8080';
const token_name = 'infmed_token';

@Injectable()
export class AuthService {

    private loggedUsername: string;
    private _isAuthenticated: boolean = false;
    private _token: string;
    private _role : string;

    constructor(private http: HttpClient) {
        //
    }

    login(username, password): Observable<HttpResponse<any>> {
        return this.http.post<any>(API_URL + '/login',
            { username: username, password: password }, { observe: 'response' })
            .pipe(tap(
                (data: HttpResponse<any>) => {
                    this.saveToken(data.headers.get('authorization'));
                    this._isAuthenticated = true;
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

    private saveToken(token): void {
        this._token = token;
        localStorage.setItem(token_name, token);
    }

    private saveUserData(token) : void {
        const decodedJWT = JWT(token);
        this.loggedUsername = decodedJWT.sub;
        this._role = decodedJWT.role;
    }

    getToken() : string {
        return this._token;
    }

    getRole() : string {
        return this._role;
    }


}