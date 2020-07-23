import {ActivatedRouteSnapshot, CanActivate, Route, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {TokenStorageService} from "../services/token-storage.service";
import {Observable} from "rxjs";

// @Injectable()
export class AuthGuard {
  // implements CanActivate{
  // constructor(private tokenStorageService : TokenStorageService, private route:Route, private state: RouterStateSnapshot) {
  // }
  // canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | boolean{
  //   if(this.tokenStorageService.getUser() == null)
  //   {
  //     return false;
  //   }
  //   return true;
  // }

}
