import { HttpErrorResponse, HttpEventType } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AnalysisComponent } from '../analysis/analysis.component';
import { TokenService } from '../service/token.service';
import { Match } from './match';
import { MatchService } from './match.service';
import { Team } from '../team';
import { Player } from '../player/player';
import { ActivatedRoute} from '@angular/router';
import { ActionTypes } from 'src/app/constantes/actionTypes';
import { PlayerService } from '../player/player.service';
import { Action } from '../action';

@Component({
  selector: 'app-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.css'],
})
export class MatchComponent implements OnInit {
  matches!: Match[];
  toShowMatch!: Match;
  toAnalizeMatch!: Match;
  analysisComponent!: AnalysisComponent;
  file!: File;
  videoLink: string = '';
  uploadLoading: boolean = false;
  videoToAdd!: string;
  public toDeleteMatch! : Match;
  public isLogged! : boolean;
  math!: Math;
  rivalTeam: Player[] = [];
  videoPendienteDeSubir?: File;
  idMatchVideoPendiente? : number;
  progress : number = 0;
  matchVideoInProgress? : number;
  playerHash?: string | null;
  players : Player[] = [];
  initial11 : Player[] = [];

  actionTypes = ActionTypes;

  constructor(private matchService: MatchService,
    private tokenService: TokenService,
    private router: ActivatedRoute,
    private playerService : PlayerService){

    }

  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.isLogged = true;

      this.getMatches();
      this.getPlayers();
    } else {
      this.playerHash = this.router.snapshot.paramMap.get('id');
      this.getMatchesForPlayer(this.playerHash);
    }

  }

  getMatchesForPlayer(playerHash: string | null) {
    throw new Error('Method not implemented.');
  }

  public getMatches(): void {
    this.matchService.getMatchs().subscribe(
      (response: Match[]) => {
        this.matches = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
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

  onOpenModal(match: Match, mode : string): void {
    this.getMatches();

    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if(mode == 'delete'){
      button.setAttribute('data-target', '#deleteMatchModal');
      this.toDeleteMatch = match;
    }else if(mode == 'show'){
      this.toShowMatch = match;
    button.setAttribute('data-target', '#showMatchModal');
    }

    container!.appendChild(button);
    button.click();
  }

  analize(match: Match) {
    this.toAnalizeMatch = match;
  }

  onChange(event: any) {
    this.file = event.target.files[0];
  }





  public onAddMatch(addForm: NgForm): void {
    document.getElementById('add-match-form')!.click();
    let match = addForm.value;
    let videoName : string = "";
    if (this.videoToAdd !== "") {
      let matchToAdd : Match = {
        video: this.videoToAdd,
        visitantTeam: {
          name: match.name,
          category: match.category,
          players: this.rivalTeam
        },
        date: match.date,
        matchNum: match.matchNum,
        actions: this.crearAcciones11Inicial(),
      }
      this.matchService.addMatch(matchToAdd).subscribe(
        (response: Match) => {
          console.log(response);
          this.getMatches();
          addForm.reset();
          this.rivalTeam = [];
          this.idMatchVideoPendiente = response.id;
          this.matchVideoInProgress = response.id;
          this.matchService.uploadVideo(this.videoPendienteDeSubir, this.idMatchVideoPendiente ).subscribe(
            (event) => {
              while(response == null){}
              response
              console.log(response);
              this.videoPendienteDeSubir = undefined;
              this.idMatchVideoPendiente = undefined;


              if(event.type == HttpEventType.UploadProgress){
                var eventTotal = event.total ? event.total : 0;
                this.progress = Math.round(event.loaded / eventTotal * 100);
                if(this.progress == 100){
                  this.progress = 0;
                  this.matchVideoInProgress = undefined;
                }
              }

            },
            (error: HttpErrorResponse) => {
              alert(error.message);

            }
          );
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
          addForm.reset();
        }
      );

    }



  }

  public addPlayerToTeam(addForm: NgForm){

    let existeNum : boolean = false;

    this.rivalTeam.forEach(element => {
      if(element.number == addForm.value.number)
        existeNum = true
      });

    if(!existeNum){
      let player: Player = {
        name : addForm.value.name,
        surname : addForm.value.surname,
        number : addForm.value.number
      };
      this.rivalTeam.push(player);
      addForm.reset();
    }else{
      alert("Ya existe un jugador con el número introducido");
    }


  }

  onFileSelected(event: any): void {

    if (event.target.files !== null) {
      this.videoPendienteDeSubir = event.target.files[0];
    }

  }


  deleteMatch(match : Match){
    this.matchService.deleteMatch(match.id).subscribe(
      (response: Match) => {
        console.log(response);
        this.getMatches();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  deletePlayer(playerNumber : number){
    this.rivalTeam.splice(this.rivalTeam.findIndex(element => {
      if(element.number == playerNumber)
        return true;
      else
        return false;
    }));
  }

  obtenerMin(min : number){
    return Math.trunc(min/60);
  }

  public devolverDescByName(name : string){
    return ActionTypes.ACTION_TYPES.find(item => item.name == name)?.description;
  }

  public playerToInit(addPlayerInitForm : NgForm){
     let index = -1;
     index = this.initial11.findIndex(p => {
      if(p.dni == addPlayerInitForm.value.player){
        return true;
      }else{
        return false;
      }
    });
    if(index < 0){
      this.initial11.push(this.players[this.players.findIndex(p => {
        if(p.dni == addPlayerInitForm.value.player){
          return true;
        }else{
          return false;
        }
      })])
    }

  }

  crearAcciones11Inicial() : Action[] {
    let actions : Action[] = [];
    this.initial11.forEach(player => {
      let action: Action = {
        min: 0,
        type: 'INICIAL',
        player: player
      };
      actions.push(action);
    });
    return actions;
  }

  obtener11Inicial() : Player[] {
    let onceInicial : Player[] = [];
    if(this.toShowMatch != undefined && this.toShowMatch.actions != undefined){
      this.toShowMatch.actions.forEach(a => {
        if(a.type == 'INICIAL'){
          onceInicial.push(a.player)
        }
      })
    }
    return onceInicial;
  }

  obtenerSuplentes() : Player[] {
    let suplentes : Player[] =[];

    if(this.toShowMatch != undefined && this.toShowMatch.actions != undefined){

      this.toShowMatch.actions.forEach(a => {
        suplentes.push(a.player);
        if(a.type == 'INICIAL'){
          suplentes.pop();
        }
      })
    }
    return suplentes;
  }

  quitarPlayerDelOnce(n : number){
    this.initial11.splice(this.rivalTeam.findIndex(element => {
      if(element.number == n)
        return true;
      else
        return false;
    }));
  }

}


