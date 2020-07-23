import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../common/user';
import { UserServiceService } from '../services/user-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  message: string;
  registerForm :FormGroup;
  constructor(private formBuilder: FormBuilder,
              private apiService: UserServiceService) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      password: [],
      userName: [],
      lastName: [],
      firstName: [],
      email: []
    })
  }

  createUser(): User{
   let user: User = this.registerForm.value;
   return user;
  }

  register(){
    console.log(this.createUser());
    this.apiService.addUser(this.createUser()).subscribe((data :any) =>
    this.message = data.message);

    console.log(this.message);
  }
}
