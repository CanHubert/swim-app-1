import {Injectable} from '@angular/core';
import {TokenStorageService} from '../services/token-storage.service';
import {UserService} from '../services/user.service';
import {RoleService} from '../services/role.service';
import {CountryService} from '../services/country.service';
import {User} from '../common/user';
import {Observable} from 'rxjs';
import {Role} from '../common/role';
import {Country} from '../common/country';

@Injectable({
    providedIn: 'root'
})
export class ProfileFacadeService {

    constructor(private tokenStorageService: TokenStorageService,
                private userService: UserService,
                private roleService: RoleService,
                private countryService: CountryService) {
    }

    /** tokenStorageService */

    getUser(): User {
        return this.tokenStorageService.getUser();
    }
    saveUser(user: User){
      this.tokenStorageService.saveUser(user);
    }

    /** user service */

  getUserDetails(id:number) : Observable<User>{
      return this.userService.getUserDetails(id);
    }

    updateUser(user:User): Observable<void>{
      return this.userService.updateUser(user);
    }
    /** roleService */
    getRoles() : Observable<Role[]>{
      console.log('getCountries in facade');
      return this.roleService.getRoles();
    }

    /** countryService */
    getCountries() : Observable<Country[]>{
      return this.countryService.getCountries();
    }


}
