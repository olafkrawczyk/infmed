import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/authentication.service';
import { User } from '../models/user';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-myaccount',
  templateUrl: './myaccount.component.html',
  styleUrls: ['./myaccount.component.css']
})
export class MyAccountComponent implements OnInit {

  user: User;
  userForm: FormGroup;

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.createForm();
    this.authService.getAuthenticatedUserData().subscribe(
      (user: User) => {this.user = user; this.createForm(user);}
    );
  }

  createForm(user: User = null) {
    if(user !== null){
      this.userForm = new FormGroup({
        name: new FormControl(user.name, Validators.required),
        surname: new FormControl(user.surname, Validators.required),
        phoneNumber: new FormControl(user.phoneNumber, Validators.required),
        pesel: new FormControl(user.pesel, [Validators.required, Validators.minLength(11), Validators.maxLength(11)]),
        birthDate: new FormControl(user.birthDate, Validators.required),
        emailAddress: new FormControl(user.emailAddress, Validators.required),
        address: new FormGroup(
          {
            postalCode: new FormControl(user.address.postalCode, Validators.required),
            houseNumber: new FormControl(user.address.houseNumber, Validators.required),
            apartmentNumber: new FormControl(user.address.apartmentNumber ? user.address.apartmentNumber : null),
            street: new FormControl(user.address.street, Validators.required),
            city: new FormControl(user.address.city, Validators.required),
          }
        )
      });
    } else {
      this.userForm = new FormGroup({
        name: new FormControl(null, Validators.required),
        surname: new FormControl(null, Validators.required),
        phoneNumber: new FormControl(null, Validators.required),
        pesel: new FormControl(null, [Validators.required, Validators.minLength(11), Validators.maxLength(11)]),
        birthDate: new FormControl(null, Validators.required),
        emailAddress: new FormControl(null, Validators.required),
        address: new FormGroup(
          {
            postalCode: new FormControl(null, Validators.required),
            houseNumber: new FormControl(null, Validators.required),
            apartmentNumber: new FormControl(),
            street: new FormControl(null, Validators.required),
            city: new FormControl(null, Validators.required),
          }
        )
      });
    }
  }

}
