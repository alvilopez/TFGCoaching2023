import { Player } from "./player/player";

export class Action{
    id? : number
    min! : number;
    type! : String;
    player! : Player;
    imgSrc? : String;

    constructor(){}

}
