import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { Match } from '../match/match';
import { LoginUsuario } from '../models/login-usuario';
import { NuevoUsuario } from '../models/nuevo-usuario';
import { AuthService } from '../service/auth.service';
import { TokenService } from '../service/token.service';
import { Team } from '../team';
import { ToastrService } from 'ngx-toastr';
import { CoachService } from '../coach/coach.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {



  @Input()
  isLogged? : boolean;

  isLoginFail = false;
  loginUsuario!: LoginUsuario;
  nombreUsuario!: string;
  password!: string;
  roles: string[] = [];
  errMsj!: string;
  nuevoUsuario!: NuevoUsuario;
  nombre!: string;
  apellidos!: string;
  email!: string;
  dni!: string;
  team! : Team;
  teamName! : string;
  category! : string;
  profileImg? : File;

  @Output()
  cambioVariable = new EventEmitter<boolean>();

  @ViewChild('profileImg') fileInput!: ElementRef;

  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private toastr: ToastrService,
    private coachService: CoachService

  ) { }

  ngOnInit() {
    if (this.tokenService.getToken()) {
      this.isLogged = true;
      this.isLoginFail = false;
      this.roles = this.tokenService.getAuthorities();
    }
  }

  onLogin(): void {
    this.loginUsuario = new LoginUsuario(this.nombreUsuario, this.password);
    this.authService.login(this.loginUsuario).subscribe(
      (      data: { token: any; nombreUsuario: string; authorities: string[]; }) => {
        this.isLogged = true;
        this.tokenService.setToken(data.token);
        this.tokenService.setUserName(data.nombreUsuario);
        this.tokenService.setAuthorities(data.authorities);
        this.roles = data.authorities;
        this.cambioVariable.emit(this.isLogged);
      },
      (      err: { error: { message: string; }; }) => {
        this.isLogged = false;
        this.errMsj = err.error.message;

        // console.log(err.error.message);
      }
    );


  }

  onRegister(): void {

    this.nuevoUsuario = {
      nombre: this.nombre,
      apellido: this.apellidos,
      cod: "C" + this.dni,
      dni: this.dni,
      email: this.email,
      nombreUsuario: this.nombreUsuario,
      password: this.password,
      team:{
        category: this.category,
        name: this.teamName,
        cod: "T" + this.dni,
        players: []
      }
    };

    this.authService.nuevo(this.nuevoUsuario).subscribe(
      (response: any)=>{
        this.toastr.success("Usuario registrado satisfactoriamente");
        if(this.profileImg != undefined){
          this.coachService.uploadImage(this.profileImg);
        }
      },
      (error: HttpErrorResponse)=>{
        console.log(error.message);
        if(this.profileImg != undefined){
          this.coachService.uploadImage(this.profileImg).subscribe();
        }
      }
    );

    this.onLogin();

  }

  onOpenModal(mode: string ){
    if(mode == "logIn"){
      document.getElementById("logIn")!.hidden = false;
      document.getElementById("signUp")!.hidden = true;
      document.getElementById("signUpButton")?.classList.remove("active");
      document.getElementById("logInButton")?.classList.add("active");
    }if (mode == "signUp"){
      document.getElementById("logIn")!.hidden = true;
      document.getElementById("signUp")!.hidden = false;
      document.getElementById("signUpButton")?.classList.add("active");
      document.getElementById("logInButton")?.classList.remove("active");
    }
  }

  onFileSelected(event: any) {
    this.profileImg = event.target.files[0];
  }
}
