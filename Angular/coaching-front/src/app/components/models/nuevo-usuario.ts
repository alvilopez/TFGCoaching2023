import { CdkObserveContent } from "@angular/cdk/observers";
import { Team } from "../team";

export interface NuevoUsuario {
    nombre: string;
    nombreUsuario: string;
    apellido: string;
    email: string;
    password: string;
    dni: string;
    cod: string;
    team: Team;
    imgSrc?: string;
}

