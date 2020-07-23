import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: FormGroup;

  constructor(private route: ActivatedRoute,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    //  this.route.paramMap.subscribe(()=>{

    console.log('loginPage');
    this.form = this.formBuilder.group({
      name : ['', Validators.compose([Validators.required])],
      password: ['', Validators.required]
    })

  }

  onSubmit(){
    console.log(this.form.value);
  }

}
