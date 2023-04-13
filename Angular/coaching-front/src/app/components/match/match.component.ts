import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AnalysisComponent } from '../analysis/analysis.component';
import { TokenService } from '../service/token.service';
import { Match } from './match';
import { MatchService } from './match.service';

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

  constructor(private matchService: MatchService,
    private tokenService: TokenService){

    }

  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.isLogged = true;

      this.getMatches();
    } else {
      this.isLogged = false;
    }

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
    let matchToAdd : Match = new Match();
    let videoName : string = "";
    if (this.videoToAdd !== "") {

      matchToAdd.video = this.videoToAdd;
      matchToAdd.visitantTeam.name = match.name;
      matchToAdd.visitantTeam.category = match.category;
      this.matchService.addMatch(matchToAdd).subscribe(
        (response: Match) => {
          console.log(response);
          this.getMatches();
          addForm.reset();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
          addForm.reset();
        }
      );
    }
  }

  onFileSelected(event: any): void {
    document.getElementById("progress")!.hidden = false;

    if (event.target.files !== null) {
      this.matchService.uploadVideo(event.target.files[0]).subscribe(
        (response: string) => {
          while(response == null){}

          console.log(response);
          this.videoToAdd = response;

        },
        (error: HttpErrorResponse) => {
          alert(error.message);

        }
      );
    }
    document.getElementById("progress")!.hidden = true;

  }


  deleteMatch(match : Match){
    this.matchService.deleteMatch(match.cod).subscribe(
      (response: Match) => {
        console.log(response);
        this.getMatches();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  obtenerMin(min : number){
    return Math.trunc(min/60);
  }


}
