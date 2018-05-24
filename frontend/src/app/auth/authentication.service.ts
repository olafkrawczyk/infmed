import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { Observable, Subject } from 'rxjs';
import { tap } from 'rxjs/operators'
import { User } from '../models/user';

import * as JWT from 'jwt-decode';
import { Router } from '@angular/router';

export const API_URL = 'http://localhost:8080';
const token_name = 'infmed_token';

export interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
    loginRequired: boolean;
}

export const ROUTES: RouteInfo[] = [
    { path: 'examinations', title: 'Examinations', icon: 'ti-notepad', class: '', loginRequired: true },
    { path: 'mydoctors', title: 'My Doctors', icon: 'ti-id-badge', class: '', loginRequired: true },
    { path: 'myaccount', title: 'My account', icon: 'ti-user', class: '', loginRequired: true },
    { path: 'login', title: 'Sign in', icon: 'ti-unlock', class: '', loginRequired: false },
    { path: 'register', title: 'New account', icon: 'ti-pencil', class: '', loginRequired: false },
    // { path: 'dashboard', title: 'Dashboard',  icon: 'ti-panel', class: '' },
    // { path: 'user', title: 'User Profile',  icon:'ti-user', class: '' },
    // { path: 'table', title: 'Table List',  icon:'ti-view-list-alt', class: '' },
    // { path: 'typography', title: 'Typography',  icon:'ti-text', class: '' },
    // { path: 'icons', title: 'Icons',  icon:'ti-pencil-alt2', class: '' },
    // { path: 'maps', title: 'Maps',  icon:'ti-map', class: '' },
    // { path: 'notifications', title: 'Notifications',  icon:'ti-bell', class: '' }
];


@Injectable()
export class AuthService {

    private loggedUsername: string;
    private _isAuthenticated: boolean = false;
    private _token: string;
    private _role: string;
    public routesSubject: Subject<RouteInfo[]> =  new Subject<RouteInfo[]>();

    constructor(private http: HttpClient, private router: Router) {
        const token = localStorage.getItem(token_name);
        const date = Date.now() / 1000 | 0;
        if (token != null && JWT(token).exp > date) {
            this.authenticate(token);
        }
    }

    get username(): string {
        return this.loggedUsername;
    }

    private authenticate(token): void {
        this.saveToken(token);
        this.saveUserData(token)
        this._isAuthenticated = true;
        this.getRoutes();
    }

    private saveToken(token): void {
        this._token = token;
        localStorage.setItem(token_name, token);
    }

    private saveUserData(token): void {
        const decodedJWT = JWT(token);
        this.loggedUsername = decodedJWT.sub;
        this._role = decodedJWT.role;
    }

    login(username, password): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${API_URL}/login`,
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
        this.router.navigate(['/login']);
        this.getRoutes();
    }

    isAuthenticated(): boolean {
        return this._isAuthenticated;
    }

    getToken(): string {
        return this._token;
    }

    getRole(): string {
        return this._role;
    }

    getRoutes() {
        let menuItems = ROUTES.filter((menuItem) => {
            if (this.isAuthenticated() && menuItem.loginRequired) {
                return menuItem;
            } else if (!this.isAuthenticated() && !menuItem.loginRequired) {
                return menuItem;
            }
        });
        this.routesSubject.next(menuItems);
    }
    getAuthenticatedUserData(){
        return this.getUserData(this.username);
    }

    getUserData(username){
        return this.http.get(`${API_URL}/user/${username}`);
    }

}