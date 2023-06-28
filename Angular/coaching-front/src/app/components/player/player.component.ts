import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { Player } from './player';
import { PlayerService } from './player.service';
import { NgForm } from '@angular/forms';
import { Action } from '../action';

import { Team } from '../team';
import { TokenService } from '../service/token.service';

import * as XLSX from "xlsx";

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent {



  public players!: Player[];
  public nullPlayer!: Player;
  public toDeletePlayer!: Player;
  public toUpdatePlayer!: Player;
  public actions!: Action[];
  public isLogged! : boolean;

  constructor(private playerService: PlayerService,
    private tokenService: TokenService){

    }






  ngOnInit(){
    if (this.tokenService.getToken()) {
      this.isLogged = true;
      this.getPlayers();
      this.getStats();
    } else {
      this.isLogged = false;
    }

  }

  public getPlayers(): void{
    this.playerService.getPlayers().subscribe(
      (response: Player[]) => {
        this.players = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public onAddPlayer(addForm: NgForm): void {
    document.getElementById('add-player-form')!.click();
    this.playerService.addPlayer(addForm.value).subscribe(
      (response: Player) => {
        console.log(response);
        this.getPlayers();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

  public onUpdatePlayer(player: Player): void {
    this.playerService.updatePlayer(player).subscribe(
      (response: Player) => {
        console.log(response);
        this.getPlayers();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeletePlayer(playerDni: string | undefined): void {
    this.playerService.deletePlayer(playerDni).subscribe(
      (response: Player) => {
        console.log(response);
        this.getPlayers();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }




  public onOpenModal(player: Player, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addPlayerModal');
    }
    if (mode === 'update') {
      this.toUpdatePlayer = player;
      button.setAttribute('data-target', '#updatePlayerModal');
    }
    if (mode === 'delete') {
      this.toDeletePlayer = player;
      button.setAttribute('data-target', '#deletePlayerModal');
    }
    container!.appendChild(button);
    button.click();
  }

  getStats(){
    var stats! : String[][];
    var result! : String[][];

    this.playerService.getStats().subscribe(
      (response: Action[]) => {
        this.actions = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )

  }

  selectStat(p : String | undefined, action : String ) : number{
    let contador : number = 0;
    for(let i = 0; i < this.actions.length; i++){
      if(this.actions[i].player.dni == p && this.actions[i].type == action){
        contador++;
      }
    }
    return contador;
  }

}
