import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, ActivatedRoute } from '@angular/router';
import { Injectable } from '@angular/core';
import { AuthService } from './authentication.service';

@Injectable()
export class AuthGuardService implements CanActivate {

    constructor(private router:Router,
        private authService : AuthService,
        private activatedRoute : ActivatedRoute) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) : boolean {
        if(!this.authService.isAuthenticated()) {
            this.router.navigate(['/login'], { queryParams : {returnUrl : state.url}});
            return false;
        }
        return true;
    }
}