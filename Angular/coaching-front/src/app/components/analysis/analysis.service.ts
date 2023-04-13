import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Action } from '../action';
import { TokenService } from '../service/token.service';

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

  public addAction(action : Action, matchCod : string){
    return this.http.post<any>(`${this.apiServerUrl}/match/addAction/${matchCod}`, action)
  }

  public getVideo(match : string){
    let headers! : HttpHeaders;
    headers.set('Authorization', 'Bearer ' + this.tokenService.getToken())
    return this.http.get<any>(`${this.apiServerUrl}video/${match}`)
  }
}
