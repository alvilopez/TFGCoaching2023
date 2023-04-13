import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';


import { Match } from '../match/match';
import { LoginUsuario } from '../models/login-usuario';
import { NuevoUsuario } from '../models/nuevo-usuario';
import { AuthService } from '../service/auth.service';
import { TokenService } from '../service/token.service';
import { Team } from '../team';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {



  isLogged = false;
  isLoginFail = false;
  loginUsuario!: LoginUsuario;
  nombreUsuario!: string;
  password!: string;
  roles: string[] = [];
  errMsj!: string;
  nuevoUsuario!: NuevoUsuario;
  nombre!: string;
  email!: string;
  dni!: string;
  team! : Team;
  teamName! : string;
  category! : string;

  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router,

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


        this.router.navigate(['/']);
      },
      (      err: { error: { message: string; }; }) => {
        this.isLogged = false;
        this.errMsj = err.error.message;

        // console.log(err.error.message);
      }
    );
  }

  onRegister(): void {

    this.nuevoUsuario = new NuevoUsuario(this.nombre, this.nombreUsuario, this.email, this.password, this.dni, new Team(this.teamName, this.category, "T" + this.dni));
    this.authService.nuevo(this.nuevoUsuario).subscribe(
      data => {


        this.router.navigate(['/login']);
      },
      err => {
        this.errMsj = err.error.mensaje;

        // console.log(err.error.message);
      }
    );
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
}
