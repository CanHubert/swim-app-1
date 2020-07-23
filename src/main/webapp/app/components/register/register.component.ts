import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Message} from "../../helpers/Message";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: any= {};
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  message: Message;
  constructor(private authService: AuthService) { }

  ngOnInit() {

  }

  onSubmit(){
    this.authService.register(this.form).subscribe(
      data=>{
              console.log(data);
              this.isSuccessful = true;
              this.isSignUpFailed = false;
              this.message = data;
      },
      err =>{
              this.errorMessage = err.error.message;
              this.isSignUpFailed = true;
      }
    );
  }

}
