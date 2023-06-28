import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Action } from '../action';
import { TokenService } from '../service/token.service';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnalysisService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient,
    private tokenService: TokenService) { }

  public uploadVideo(file : File){
    const formData: FormData = new FormData();

    formData.append('file', file)

    return this.http.post<any>(`${this.apiServerUrl}/match/video`, formData)
  }

  public addAction(action : Action, matchId : number | undefined){
    return this.http.post<any>(`${this.apiServerUrl}/match/action/${matchId}`, action)
  }

  public getVideo(match : string | undefined){
    let headers! : HttpHeaders;
    headers.set('Authorization', 'Bearer ' + this.tokenService.getToken())
    return this.http.get<any>(`${this.apiServerUrl}/video/${match}`)
  }

  public downloadActionImage(action: Action){
    return this.http.get<Action>(`${this.apiServerUrl}/match/video/photo/${action.id}`);
  }

  public deleteAction(action : Action){
    return this.http.delete<Action>(`${this.apiServerUrl}/match/action/${action.id}`)
  }
}
