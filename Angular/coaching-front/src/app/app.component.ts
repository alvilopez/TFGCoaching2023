import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
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

  constructor(private playerService: PlayerService,
    private tokenService: TokenService,
    private router: Router){

    this.router.navigate(['/coach']);
    this.componentsMap.set("home", true);
    this.componentsMap.set("players", false);
    this.componentsMap.set("matches", false);
    this.componentsMap.set("analysis", false);
    this.componentsMap.set("rivalTeams", false);
    this.componentsMap.set("stats", false);
    }

  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.isLogged = true;
    } else {
      this.isLogged = false;
    }
  }


  public onOpenModal(mode: string): void {
    let screensMap = new Map<string, HTMLElement>();

    screensMap.set('home', document.getElementById('home')!);
    screensMap.set('players', document.getElementById('pestañaPlayers')!);
    screensMap.set('matches', document.getElementById('pestañaMatches')!);
    screensMap.set('rivalTeams', document.getElementById('pestañaRivalTeams')!);
    screensMap.set('stats', document.getElementById('pestañaStats')!);

    screensMap.forEach((value: HTMLElement, key: string) => {
      if (key == mode) {value.classList.add('current');}
      else value.classList.remove('current');
    });

    this.componentsMap.forEach((value:Boolean, key: string) => {
      if (key == mode) this.componentsMap.set(key,true);
      else this.componentsMap.set(key,false);
    })


  }
}
