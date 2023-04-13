import { CdkObserveContent } from "@angular/cdk/observers";
import { Team } from "../team";

export class NuevoUsuario {
    nombre: string;
    nombreUsuario: string;
    email: string;
    password: string;
    dni: string;
    cod: string;
    team: Team;
    constructor(nombre: string, nombreUsuario: string, email: string, password: string, dni: string, team: Team) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.dni =dni;
        this.cod = "C" + dni;
        this.team = team;
    }
}
