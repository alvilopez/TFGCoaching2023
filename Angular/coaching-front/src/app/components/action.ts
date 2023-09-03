import { Player } from "./player/player";

export class Action{
    id? : number
    min! : number;
    type! : string;
    player! : Player;
    imgSrc? : string;

    constructor(){}

}
