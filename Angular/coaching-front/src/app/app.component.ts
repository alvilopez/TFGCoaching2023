import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { PlayerComponent } from './components/player/player.component';
import { PlayerService } from './components/player/player.service';
import { NgForm } from '@angular/forms';
import { identifierName } from '@angular/compiler';
import { TokenService } from './components/service/token.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit{



  logged = false;

  componentsMap = new Map<string, Boolean>;
  public isLogged! : boolean;
  public mostrarLogin : boolean = false;
  public mostrarWelcome: boolean = true;
  public  screensMap = new Map<string, HTMLElement>();

  constructor(private playerService: PlayerService,
    private tokenService: TokenService,
    private router: Router,
    private cdr: ChangeDetectorRef){

    if(this.isLogged){
      this.router.navigate(['']);
      this.mostrarWelcome = false;
      this.componentsMap.set("welcome", false);
      this.componentsMap.set("coach", true);
    }else{
      this.componentsMap.set("welcome", true);
      this.componentsMap.set("coach", false);
    }


    this.componentsMap.set("home", false);
    this.componentsMap.set("players", false);
    this.componentsMap.set("matches", false);
    this.componentsMap.set("analysis", false);
    this.componentsMap.set("stats", false);
    this.componentsMap.set("rivalTeams", false);


    }

  ngOnInit(): void {
    if (this.tokenService.getToken()) {

      this.isLogged = true;
      this.mostrarWelcome = false;
      this.cdr.detectChanges


    } else {
      this.isLogged = false;
    }
  }


  public onOpenModal(mode: string): void {


    if(this.isLogged){
      this.screensMap.set('coach', document.getElementById('pestañaCoach')!);
      this.screensMap.set('players', document.getElementById('pestañaPlayers')!);
      this.screensMap.set('matches', document.getElementById('pestañaMatches')!);
      this.screensMap.set('stats', document.getElementById('pestañaStats')!);
      // this.screensMap.set('rivalTeams', document.getElementById('pestañaRivalTeams')!);

    }else{
      this.screensMap.set('welcome', document.getElementById('welcome')!);
      this.screensMap.set('home', document.getElementById('home')!);
    }


    this.screensMap.forEach((value: HTMLElement, key: string) => {
      if (key == mode) {value.classList.add('current');}
      else{
        value.classList.remove('current');
      }
    });

    this.componentsMap.forEach((value:Boolean, key: string) => {
      if (key == mode) this.componentsMap.set(key,true);
      else this.componentsMap.set(key,false);
    })

    if(mode == 'home'){
      this.mostrarLogin = true;
      this.mostrarWelcome = false;
    }else if(mode == 'welcome'){
      this.mostrarWelcome = true;
      this.mostrarLogin = false;
    }else{
      this.mostrarLogin = false;
      this.mostrarWelcome = false;
    }
    this.cdr.detectChanges();

  }


  manejarCambioVariable(event: boolean) {
    this.isLogged = event;
    this.router.navigate(['/coach']);
    this.cdr.detectChanges();
    this.onOpenModal('coach');

  }


}
