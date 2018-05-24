import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SpinnerService {

  visible : boolean = false;

  constructor() { }

  show(){
    this.visible = true;
  }

  hide() {
    this.visible = false;
  }

}
