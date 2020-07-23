export class Children {
    id: number;
    firstName: string;
    lastName: string;
    dateOfBirth: string;

    constructor(children: any) {
        this.id = children.id;
        this.firstName = children.firstName;
        this.lastName = children.lastName;
        this.dateOfBirth = children.dateOfBirth;
    }
}
