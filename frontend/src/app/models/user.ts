import { Address } from "./address";

export interface User {
    uuid: string,
    name: string,
    surname: string,
    pesel: string,
    birthDate: Date,
    phoneNumber: string,
    emailAddress: string,
    username: string,
    address: Address
};