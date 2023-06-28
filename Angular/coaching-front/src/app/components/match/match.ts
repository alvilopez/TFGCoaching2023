import { Action } from "../action";
import { Team } from "../team";

export class Match{


   cod? : string;
   id? : number;
   date! : Date;
   matchNum! : number;
   video! : string;


   localTeam? : Team;
   visitantTeam! : Team;

   actions! : Action[];

  }
