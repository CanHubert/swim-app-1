import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from "../../services/token-storage.service";

@Component({
  selector: 'app-price-list',
  templateUrl: './price-list.component.html',
  styleUrls: ['./price-list.component.css']
})
export class PriceListComponent implements OnInit {

  isLogged = false;
  constructor(private  tokenStorageService : TokenStorageService) { }

  ngOnInit() {
    if(this.tokenStorageService.getUser() != null)
    {
      this.isLogged = true;
    }
  }

}
