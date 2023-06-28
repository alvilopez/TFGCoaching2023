import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-rival-teams',
  templateUrl: './rival-teams.component.html',
  styleUrls: ['./rival-teams.component.css']
})
export class RivalTeamsComponent implements OnInit {

  public isLogged! : boolean;

  constructor() { }

  ngOnInit(): void {
  }

}
