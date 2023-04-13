import { Player } from "./player/player";

export class Team{
    cod : string;

    name : string;
    category : string;


    players : Player[];


    constructor(cod : string, name : string, category : string){
      this.cod = cod;
      this.name = name;
      this.category = category;
      this.players = [];
    }

}
