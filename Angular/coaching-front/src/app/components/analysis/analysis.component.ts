import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, ElementRef, Inject, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UrlCreationOptions } from '@angular/router';



import { Action } from '../action';
import { Match } from '../match/match';
import { Player } from '../player/player';
import { TokenService } from '../service/token.service';
import { AnalysisService } from './analysis.service';
import { ActionTypes } from 'src/app/constantes/actionTypes';
import { saveAs } from 'file-saver';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-analysis',
  templateUrl: './analysis.component.html',
  styleUrls: ['./analysis.component.css']
})

export class AnalysisComponent implements OnInit{

  actionTypes = ActionTypes;
  base64Image: any;
  actionToShowPhoto? : Action;

  popupImageSrc : string | undefined;
  popupVisible = false;

    openImgPopup(imageSrc: string) {
      this.popupImageSrc = imageSrc;
      this.popupVisible = true;
    }

    closePopup() {
      this.popupVisible = false;
    }

  constructor(private analysisService : AnalysisService,
    private tokenService: TokenService,
    private cdr: ChangeDetectorRef,
    private toastr: ToastrService,
    private http: HttpClient
     ) { }

  ngOnInit(): void {
    this.actionToShowPhoto = undefined;



  }

  @Input() toAnalizeMatch! : Match;

  public getVideo(match? : number ){
    return environment.apiBaseUrl + "/video/" + this.tokenService.getUserName() + "/" + match;
  }

  public getFoto(action? : String){
    console.log( environment.apiBaseUrl + "/files/" + this.tokenService.getUserName() + "/"  + action);

    return environment.apiBaseUrl + "/files/" + this.tokenService.getUserName() + "/"  + action;
  }


  public onAddAction(addForm : NgForm){



    var actionToAdd : Action = new Action();
    var video : HTMLMediaElement;
    video = document.getElementById('video') as HTMLMediaElement;


    if(this.validarSiJugadorEnElCampo(addForm.value.player, addForm.value.type, video.currentTime)){
      for(var i = 0;this.toAnalizeMatch.localTeam!= undefined && i < this.toAnalizeMatch.localTeam.players.length; i++){
        if(this.toAnalizeMatch.localTeam.players[i].dni == addForm.value.player){
          actionToAdd.player = this.toAnalizeMatch.localTeam.players[i];
        }
      }

      actionToAdd.type = addForm.value.type;
      actionToAdd.min = Math.trunc(video.currentTime);

      document.getElementById('add-action-form')!.click();
      this.analysisService.addAction(actionToAdd, this.toAnalizeMatch.id).subscribe(
        (response: Action) => {
          console.log(response);
          addForm.reset();
          this.toAnalizeMatch.actions.push(response);
          actionToAdd = response;
          this.solicitarFotoAccion(response);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
          addForm.reset();
        }
      );
    }else{
      this.toastr.error('¡El jugador no está jugando!', 'Error', {
        closeButton: true,
        progressBar: true,
        newestOnTop: true,
        timeOut: 5000,
        positionClass: 'toast-top-right'

      });
      addForm.reset();
    }
  }

  validarSiJugadorEnElCampo(dni: string, type: string, min : number ) : Boolean {
    let accionesEntrarSalir : Action[] = [];

    this.toAnalizeMatch.actions.forEach(a => {
      if((dni == a.player.dni) && (a.type == 'INICIAL' || a.type == 'CAMBIO_ENTRA' || a.type == 'CAMBIO_SALE')){
        accionesEntrarSalir.push(a);
      }
    });

    if(accionesEntrarSalir.length == 0 && !(type == 'CAMBIO_ENTRA')){
      return false;
    }if(accionesEntrarSalir.length == 1 && type == 'CAMBIO_ENTRA'){
      return false;
    }if(accionesEntrarSalir.length == 2){
      let minSalida : number;

      if(accionesEntrarSalir[0].min > accionesEntrarSalir[1].min){
        minSalida = accionesEntrarSalir[0].min;
      }else{
        minSalida = accionesEntrarSalir[1].min;
      }

      if(minSalida < min){
        return false;
      }

    }

    return true;
  }

  public onAddCambio(addForm : NgForm){

    var actionToAdd : Action = new Action();
    var video : HTMLMediaElement;
    video = document.getElementById('video') as HTMLMediaElement;

    var dniEntra : string;
    var dniSale : string;

    if(addForm.value.type == 'CAMBIO_ENTRA'){
      dniEntra = addForm.value.player;
      dniSale = addForm.value.player2;
    }else{
      dniSale = addForm.value.player;
      dniEntra = addForm.value.player2;
    }

    if(this.validarSiJugadorEnElCampo(dniEntra, 'CAMBIO_ENTRA', video.currentTime) && this.validarSiJugadorEnElCampo(dniSale, 'CAMBIO_SALE', video.currentTime)){

      for(var i = 0;this.toAnalizeMatch.localTeam!= undefined && i < this.toAnalizeMatch.localTeam.players.length; i++){
        if(this.toAnalizeMatch.localTeam.players[i].dni == addForm.value.player){
          actionToAdd.player = this.toAnalizeMatch.localTeam.players[i];
        }
      }

      actionToAdd.type = addForm.value.type;
      actionToAdd.min = Math.trunc(video.currentTime);

      // document.getElementById('add-action-form')!.click();
      this.analysisService.addAction(actionToAdd, this.toAnalizeMatch.id).subscribe(
        (response: Action) => {
          console.log(response);
          addForm.reset();
          this.toAnalizeMatch.actions.push(response);
          actionToAdd = response;
          this.solicitarFotoAccion(response);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
          addForm.reset();
        }
      );

      for(var i = 0;this.toAnalizeMatch.localTeam!= undefined && i < this.toAnalizeMatch.localTeam.players.length; i++){
        if(this.toAnalizeMatch.localTeam.players[i].dni == addForm.value.player2){
          actionToAdd.player = this.toAnalizeMatch.localTeam.players[i];
        }
      }

      actionToAdd.type = addForm.value.type2;

      this.analysisService.addAction(actionToAdd, this.toAnalizeMatch.id).subscribe(
        (response: Action) => {
          console.log(response);
          addForm.reset();
          this.toAnalizeMatch.actions.push(response);
          actionToAdd = response;
          this.solicitarFotoAccion(response);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
          addForm.reset();
        }
      );

    }else{

    }


  }

  obtenerMin(min : number){
    return Math.trunc(min/60);
  }

  irAMinuto(min : number){
    var video : HTMLMediaElement;
    video = document.getElementById('video') as HTMLMediaElement;
    video.currentTime = (min-5) < 0 ? 0 : min-5;
  }

  solicitarFotoAccion(action : Action){
    this.actionToShowPhoto = undefined;
    if(action.imgSrc == undefined){

      this.analysisService.downloadActionImage(action).subscribe((response : Action) => {
        let actionIndex : number = this.toAnalizeMatch.actions.findIndex(a=>{
          if(a.id == response.id)
            return true;
          else
            return false;
        });

        this.toAnalizeMatch.actions[actionIndex] = response;
        this.cdr.detectChanges();

      }, error => {
          console.log(error);
      });




    }else{
      this.actionToShowPhoto = action;
    }
  }

  public deleteAction(action : Action){
    this.analysisService.deleteAction(action).subscribe(response =>{
      let actionIndex : number = this.toAnalizeMatch.actions.findIndex(a=>{
        if(a.id == response.id)
          return true;
        else
          return false;
      });

      this.toAnalizeMatch.actions.splice(actionIndex);

      });

  }


  public devolverDescByName(name : string){
    return ActionTypes.ACTION_TYPES.find(item => item.name == name)?.description;
  }

  obtenerAccionesDurantePartido() : Action[]{
    let accionesJuegoEnCurso: Action[] = [];
    this.toAnalizeMatch.actions.forEach(a=>{
      if(a.type != 'INICIAL'){
       accionesJuegoEnCurso.push(a);
      }

    })
    return accionesJuegoEnCurso;
  }
  obtenerAccionesDurantePartidosSinCambios() : Action[]{
    let accionesJuegoEnCurso: Action[] = [];
    this.toAnalizeMatch.actions.forEach(a=>{
      if(a.type != 'INICIAL' &&
        a.type != 'CAMBIO_ENTRA' &&
        a.type != 'CAMBIO_SALE'){
       accionesJuegoEnCurso.push(a);
      }

    })
    return accionesJuegoEnCurso;
  }

  descargarArchivo() {
    // Reemplaza la URL con la dirección de tu servicio Java y la ruta apropiada
   // Reemplaza la URL con la dirección de tu servicio Java y la ruta apropiada
   const url = 'http://localhost:8080/match/video/split/' + this.toAnalizeMatch.cod;

   // Realiza la solicitud HTTP para descargar el archivo
    this.http.get(url, { responseType: 'blob' }).subscribe((data: Blob) => {
     const blob = new Blob([data], { type: 'application/zip' });

     // Crea una URL de objeto para el blob
     const urlBlob = window.URL.createObjectURL(blob);

     // Crea un enlace HTML para descargar el archivo
     const a = document.createElement('a');
     a.href = urlBlob;
     a.download = 'carpeta.zip';

     // Simula un clic en el enlace para iniciar la descarga
     a.click();

     // Limpia la URL del objeto después de la descarga
     window.URL.revokeObjectURL(urlBlob);
   });

  }

}

