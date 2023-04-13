import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Match } from './match';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getMatchs() : Observable<Match[]>{
    return this.http.get<any>(`${this.apiServerUrl}/match`);
  }

  public uploadVideo(file : File) : Observable<string>{
    const formData: FormData = new FormData();

    formData.append('file', file);

    return this.http.post<any>(`${this.apiServerUrl}/match/video`, formData);

  }

  public addMatch(match : Match) : Observable<Match>{

    return this.http.post<Match>(`${this.apiServerUrl}/match`, match);

  }

  // public addMatch(match: Match) : Observable<Match>{
  //   return this.http.post<Match>(`${this.apiServerUrl}/match`, match);
  // }

  // public updateMatch(match: Match) : Observable<Match>{
  //   return this.http.put<Match>(`${this.apiServerUrl}/match`, match);
  // }

  // public deleteMatch(dni: string) : Observable<Match>{
  //   return this.http.delete<Match>(`${this.apiServerUrl}/match/${dni}`);
  // }


  public deleteMatch(matchCod : string) : Observable<Match>{
    return this.http.delete<Match>(`${this.apiServerUrl}/match/${matchCod}`);
  }
}
