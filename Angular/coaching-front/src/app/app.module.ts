import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { PlayerComponent } from './components/player/player.component';

import { MatchComponent } from './components/match/match.component';
import { HomeComponent } from './components/home/home.component';
import { AnalysisComponent } from './components/analysis/analysis.component';



import {VgCoreModule} from '@videogular/ngx-videogular/core';
import {VgControlsModule} from '@videogular/ngx-videogular/controls';
import {VgOverlayPlayModule} from '@videogular/ngx-videogular/overlay-play';
import {VgBufferingModule} from '@videogular/ngx-videogular/buffering';

import {MatNativeDateModule} from '@angular/material/core';
import {MaterialExampleModule} from '../material.module';
import { RouterModule, Routes } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { interceptorProvider, InterceptorService } from './components/interceptors/interceptor.service';
import { CoachComponent } from './components/coach/coach.component';
import { RivalTeamsComponent } from './components/rival-teams/rival-teams.component';
import { StatsComponent } from './components/stats/stats.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { PhotoEditorComponent } from './components/photo-editor/photo-editor.component';
import { ToastrModule } from 'ngx-toastr';





@NgModule({
  declarations: [
    AppComponent,
    MatchComponent,
    PlayerComponent,
    HomeComponent,
    AnalysisComponent,
    CoachComponent,
    CoachComponent,
    RivalTeamsComponent,
    StatsComponent,
    StatsComponent,
    WelcomeComponent,
    PhotoEditorComponent,


  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    VgCoreModule,
    VgControlsModule,
    VgOverlayPlayModule,
    VgBufferingModule,
    MatNativeDateModule,
    MaterialExampleModule,
    ReactiveFormsModule,
    AppRoutingModule,
    ToastrModule.forRoot()
  ],
  providers : [interceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
