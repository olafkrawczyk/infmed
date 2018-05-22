import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from '../auth/authentication.service';
import { Subscription } from 'rxjs';

declare var $:any;


@Component({
    moduleId: module.id,
    selector: 'sidebar-cmp',
    templateUrl: 'sidebar.component.html',
})

export class SidebarComponent implements OnInit, OnDestroy {
    public menuItems: any[];
    private subscription : Subscription;

    constructor(private authService : AuthService) {
        
    }

    ngOnInit() {
        this.subscription = this.authService.routesSubject.subscribe(
            (routes) => {
                this.menuItems = [...routes];
            }
        );
        this.authService.getRoutes();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
    isNotMobileMenu(){
        if($(window).width() > 991){
            return false;
        }
        return true;
    }

}
