import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, ElementRef, Inject, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UrlCreationOptions } from '@angular/router';



import { Action } from '../action';
import { Match } from '../match/match';
import { Player } from '../player/player';
import { TokenService } from '../service/token.service';
import { AnalysisService } from './analysis.service';
import { ActionTypes } from 'src/app/constantes/actionTypes';
import { saveAs } from 'file-saver';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs';


@Component({
  selector: 'app-analysis',
  templateUrl: './analysis.component.html',
  styleUrls: ['./analysis.component.css']
})

export class AnalysisComponent implements OnInit{

  actionTypes = ActionTypes;
  base64Image: any;
  actionToShowPhoto? : Action;

  popupImageSrc : string | undefined;
  popupVisible = false;

    openImgPopup(imageSrc: string) {
      this.popupImageSrc = imageSrc;
      this.popupVisible = true;
    }

    closePopup() {
      this.popupVisible = false;
    }

  constructor(private analysisService : AnalysisService,
    private tokenService: TokenService,
    private cdr: ChangeDetectorRef
     ) { }

  ngOnInit(): void {
    this.actionToShowPhoto = undefined;

  }

  @Input() toAnalizeMatch! : Match;

  public getVideo(match? : number ){
    return environment.apiBaseUrl + "/video/" + this.tokenService.getUserName() + "/" + match;
  }

  public getFoto(action? : String){
    console.log( environment.apiBaseUrl + "/files/" + this.tokenService.getUserName() + "/"  + action);

    return environment.apiBaseUrl + "/files/" + this.tokenService.getUserName() + "/"  + action;
  }

  public addAction(type : String, min : number){

  }
  public onAddAction(addForm : NgForm){



    var actionToAdd : Action = new Action();
    var video : HTMLMediaElement;
    video = document.getElementById('video') as HTMLMediaElement;




    for(var i = 0;this.toAnalizeMatch.localTeam!= undefined && i < this.toAnalizeMatch.localTeam.players.length; i++){
      if(this.toAnalizeMatch.localTeam.players[i].dni == addForm.value.player){
        actionToAdd.player = this.toAnalizeMatch.localTeam.players[i];
      }
    }
    actionToAdd.type = addForm.value.type;
    actionToAdd.min = Math.trunc(video.currentTime);

    document.getElementById('add-action-form')!.click();
    this.analysisService.addAction(actionToAdd, this.toAnalizeMatch.id).subscribe(
      (response: Action) => {
        console.log(response);
        addForm.reset();
        this.toAnalizeMatch.actions.push(response);
        actionToAdd = response;
        this.solicitarFotoAccion(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );



  }

  obtenerMin(min : number){
    return Math.trunc(min/60);
  }

  irAMinuto(min : number){
    var video : HTMLMediaElement;
    video = document.getElementById('video') as HTMLMediaElement;
    video.currentTime = (min-5) < 0 ? 0 : min-5;
  }

  solicitarFotoAccion(action : Action){
    this.actionToShowPhoto = undefined;
    if(action.imgSrc == undefined){

      this.analysisService.downloadActionImage(action).subscribe((response : Action) => {
        let actionIndex : number = this.toAnalizeMatch.actions.findIndex(a=>{
          if(a.id == response.id)
            return true;
          else
            return false;
        });

        this.toAnalizeMatch.actions[actionIndex] = response;
        this.cdr.detectChanges();

      }, error => {
          console.log(error);
      });




    }else{
      this.actionToShowPhoto = action;
    }
  }

  public deleteAction(action : Action){
    this.analysisService.deleteAction(action).subscribe(response =>{
      let actionIndex : number = this.toAnalizeMatch.actions.findIndex(a=>{
        if(a.id == response.id)
          return true;
        else
          return false;
      });

      this.toAnalizeMatch.actions.splice(actionIndex);

      });

  }


  public devolverDescByName(name : string){
    return ActionTypes.ACTION_TYPES.find(item => item.name == name)?.description;
  }


}

