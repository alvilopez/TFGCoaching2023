import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CoachComponent } from './components/coach/coach.component';

import { HomeComponent } from './components/home/home.component';
import { MatchComponent } from './components/match/match.component';
import { PlayerComponent } from './components/player/player.component';
import { RivalTeamsComponent } from './components/rival-teams/rival-teams.component';
import { StatsComponent } from './components/stats/stats.component';
import { AnalysisComponent } from './components/analysis/analysis.component';

const routes: Routes = [
  { path: 'players', component: PlayerComponent },
  { path: 'match', component: MatchComponent },
  { path: 'analysis', component: AnalysisComponent },
  { path: 'coach', component: CoachComponent },
  { path: 'rivalTeams', component: RivalTeamsComponent},
  { path: 'stats', component: StatsComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
