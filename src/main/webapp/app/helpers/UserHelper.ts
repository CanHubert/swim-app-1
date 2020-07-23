import {User} from '../common/user';



export class UserHelper{

    static showRoles(user: User) : string[]{
        return user.roles.map(role=> role.name);
    }

    static showCountries(user:User) : string[]{
        return user.countries.map(country=> country.name);
    }
}
