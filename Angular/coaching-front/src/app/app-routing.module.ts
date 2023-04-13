import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AnalysisComponent } from './components/analysis/analysis.component';
import { CoachComponent } from './components/coach/coach.component';

import { HomeComponent } from './components/home/home.component';
import { MatchComponent } from './components/match/match.component';
import { PlayerComponent } from './components/player/player.component';

const routes: Routes = [
  { path: 'players', component: PlayerComponent },
  { path: 'match', component: MatchComponent },
  { path: 'analysis', component: AnalysisComponent },
  { path: 'coach', component: CoachComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
