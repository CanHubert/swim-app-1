export class Role {
    id : number;
    name : string;
    order: number;


    constructor(role: any) {
        if(role)
        {
            this.id = role.id;
            this.name = role.name;
            this.order = role.order;
        }
    }

}
