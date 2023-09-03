import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NuevoUsuario } from '../models/nuevo-usuario';
import { TokenService } from '../service/token.service';
import { CoachService } from './coach.service';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-coach',
  templateUrl: './coach.component.html',
  styleUrls: ['./coach.component.css']
})
export class CoachComponent implements OnInit {


  constructor(private tokenService: TokenService,
    private coachService: CoachService,
    private router: Router,
    private cdr: ChangeDetectorRef) { }

  user! : NuevoUsuario;
  isLogged = false;
  profileImg: string = environment.apiBaseUrl + "/files/" + this.tokenService.getUserName() + "/";
  cacheBuster: number = 0;
  @ViewChild('fileInput') fileInput!: ElementRef;

  ngOnInit(): void {

      if (this.tokenService.getToken()) {
        this.isLogged = true;
        this.getUser(this.tokenService.getUserName());

      } else {
        this.isLogged = false;
      }
  }

  public getFoto(img? : String){
    console.log( environment.apiBaseUrl + "/files/" + this.tokenService.getUserName() + "/"  + img);

    return environment.apiBaseUrl + "/files/" + this.tokenService.getUserName() + "/"  + img;
  }

  public getUser(userName: string){
    this.coachService.getUser(userName).subscribe(
      (response: NuevoUsuario) => {
        this.user =response;
        this.profileImg = this.profileImg + this.user.imgSrc;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }


  openFileSelector() {
    const fileInput = this.fileInput.nativeElement as HTMLInputElement;
    fileInput.click();
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      const file = event.target.files[0];
    if (file) {
      this.coachService.uploadImage(file).subscribe(
        (response: any)=>{
          if(this.user.imgSrc != undefined)
            this.cacheBuster++;
            this.profileImg = environment.apiBaseUrl + "/files/" + this.tokenService.getUserName() + "/" + this.user.imgSrc + "?" + this.cacheBuster;
        },
        (error: HttpErrorResponse)=>{
          alert(error.message)
        }
      );

      reader.readAsDataURL(file);
    }
  }

  }

  public logOut(){
    this.tokenService.logOut();
    this.router.navigate(['']);

    setTimeout(() => {
      this.cdr.detectChanges();
    }, 0);
  }

}
