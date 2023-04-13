import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, Inject, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UrlCreationOptions } from '@angular/router';



import { Action } from '../action';
import { Match } from '../match/match';
import { Player } from '../player/player';
import { TokenService } from '../service/token.service';
import { AnalysisService } from './analysis.service';


@Component({
  selector: 'app-analysis',
  templateUrl: './analysis.component.html',
  styleUrls: ['./analysis.component.css']
})

export class AnalysisComponent implements OnInit{


  constructor(private analysisService : AnalysisService,
    private tokenService: TokenService,
     ) { }

  ngOnInit(): void {
  }

  @Input() toAnalizeMatch! : Match;

  public getVideo(match : string){
    return "http://localhost:8080/video/" + this.tokenService.getUserName() + "/" + match;
  }

  public addAction(type : String, min : number){

  }
  public onAddAction(addForm : NgForm){



    var actionToAdd : Action = new Action();
    var video : HTMLMediaElement;
    video = document.getElementById('video') as HTMLMediaElement;


    for(var i = 0; i < this.toAnalizeMatch.localTeam.players.length; i++){
      if(this.toAnalizeMatch.localTeam.players[i].dni == addForm.value.player){
        actionToAdd.player = this.toAnalizeMatch.localTeam.players[i];
      }
    }
    actionToAdd.type = addForm.value.type;
    actionToAdd.min = Math.trunc(video.currentTime);

    document.getElementById('add-action-form')!.click();
    this.analysisService.addAction(actionToAdd, this.toAnalizeMatch.cod).subscribe(
      (response: Action) => {
        console.log(response);
        addForm.reset();
        this.toAnalizeMatch.actions.push(actionToAdd);


      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );

  }

}

