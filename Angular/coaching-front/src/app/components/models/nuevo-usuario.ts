import { CdkObserveContent } from "@angular/cdk/observers";
import { Team } from "../team";

export interface NuevoUsuario {
    name: string;
    nameUsuario: string;
    surname: string;
    email: string;
    password: string;
    dni: string;
    cod: string;
    team: Team;
    imgSrc?: String;
}

