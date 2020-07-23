export class Country {
    id: number;
    name : string;

    constructor(country : any) {
        this.id = country.id;
        this.name = country.name;
    }
}
