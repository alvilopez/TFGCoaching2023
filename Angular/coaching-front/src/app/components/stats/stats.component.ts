import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { PlayerService } from '../player/player.service';
import { Player } from '../player/player';
import { TokenService } from '../service/token.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Action } from '../action';
import * as XLSX from "xlsx";

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit {

  playerSelected? : Player;
  //private isLogged : boolean;
  public players!: Player[];
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


  public onOpenModal(player: Player, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if (mode === 'excel'){
      this.fireEvent();
    }
    container!.appendChild(button);
    button.click();
  }


  @ViewChild("tableStats") table!: ElementRef;
  fireEvent() {
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(
      this.table.nativeElement
    );

    /* new format */
    var fmt = "0";
    /* change cell format of range B2:D4 */
    var range = { s: { r: 1, c: 1 }, e: { r: 2, c: 100000 } };
    for (var R = range.s.r; R <= range.e.r; ++R) {
      for (var C = range.s.c; C <= range.e.c; ++C) {
        var cell = ws[XLSX.utils.encode_cell({ r: R, c: C })];
        if (!cell || cell.t != "n") continue; // only format numeric cells
        cell.z = fmt;
      }
    }
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Sheet1");
    var fmt = "@";
    wb.Sheets["Sheet1"]["F"] = fmt;

    /* save to file */
    XLSX.writeFile(wb, "SheetJS.xlsx");
  }


  isSelected(p : Player){
    this.playerSelected = p;
  }


  public ACTIONS = [
    {name : "FALTA_A_FAVOR", id : 1, description : "Falta a favor"},
    {name : "FALTA_EN_CONTRA", id : 2, description : "Falta en contra"},
    {name : "PENALTY_A_FAVOR", id : 3, description : "Penalti a favor"},
    {name : "PENALTY_EN_CONTRA", id : 4, description : "Penalti en contra"},
    {name : "ROBO_DE_BALON", id : 5, description : "Robo de balon"},
    {name : "PERDIDA_DE_BALON", id : 6, description : "Perdida de balon"},
    {name : "OCASION_A_FAVOR", id : 7, description : "Ocasion a favor"},
    {name : "OCASION_EN_CONTRA", id : 8, description : "Ocasion en contra"},
    {name : "CORNER_A_FAVOR", id : 9, description : "Corner a favor"},
    {name : "CORNER_EN_CONTRA", id : 10, description : "Corner en contra"},
    {name : "GOL_A_FAVOR", id : 11, description : "Gol a favor"},
    {name : "GOL_EN_CONTRA", id : 12, description : "Gol en contra"},
    {name : "PARADA", id :13, description : "Parada"}
  ];


}
