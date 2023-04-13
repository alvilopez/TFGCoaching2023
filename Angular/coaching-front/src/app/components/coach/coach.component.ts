import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NuevoUsuario } from '../models/nuevo-usuario';
import { TokenService } from '../service/token.service';
import { CoachService } from './coach.service';

@Component({
  selector: 'app-coach',
  templateUrl: './coach.component.html',
  styleUrls: ['./coach.component.css']
})
export class CoachComponent implements OnInit {

  constructor(private tokenService: TokenService,
    private coachService: CoachService) { }

  user! : NuevoUsuario;
  isLogged = false;

  ngOnInit(): void {

      if (this.tokenService.getToken()) {
        this.isLogged = true;
        this.getUser(this.tokenService.getUserName());

      } else {
        this.isLogged = false;
      }




  }



  public getUser(userName: string){
    this.coachService.getUser(userName).subscribe(
      (response: NuevoUsuario) => {
        this.user =response
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

}
