import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Player } from './player';
import { environment } from 'src/environments/environment';

import { Action } from '../action';


@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getPlayers() : Observable<Player[]>{
    return this.http.get<any>(`${this.apiServerUrl}/player`);
  }

  public addPlayer(player: Player) : Observable<Player>{
    return this.http.post<Player>(`${this.apiServerUrl}/player/0`, player);
  }

  public updatePlayer(player: Player | undefined) : Observable<Player>{
    return this.http.put<Player>(`${this.apiServerUrl}/player`, player);
  }

  public deletePlayer(dni: string | undefined) : Observable<Player>{
    return this.http.delete<Player>(`${this.apiServerUrl}/player/${dni}`);
  }

  public getStats() : Observable<Action[]>{
    return this.http.get<any>(`${this.apiServerUrl}/player/getStats`)
  }
}
