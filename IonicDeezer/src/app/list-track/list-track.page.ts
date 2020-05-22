import { Component, OnInit } from '@angular/core';
import { DeezerService, Track, DataSearchTrack } from '../service/deezer.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-list-track',
  templateUrl: './list-track.page.html',
  styleUrls: ['./list-track.page.scss'],
})
export class ListTrackPage implements OnInit {
  TAG: string = "ListTrackPage";
  listTrack : Track[];
  id : number;
  audio:HTMLAudioElement;

  constructor(public deezerService: DeezerService, public router : Router, private activatedRoute : ActivatedRoute) { }

  ngOnInit() {
    //Récupération de l'ID de l'album pour lancer la recherche des chansons
    this.id = Number.parseInt(this.activatedRoute.snapshot.paramMap.get('id'));
    console.log(`${this.TAG} Number.parseInt(this.activatedRoute.snapshot.paramMap.get=${this.id})`);
    this.onSearchTrack();
    this.audio = new Audio();
  }

  //Méthode qui lance la recherche de chanson à partir de l'id de l'album
  onSearchTrack(){
    this.deezerService.getTrack(this.id).then((result:DataSearchTrack) => {
      this.listTrack = result.data;
      })
  }

  play(src: any) {
    this.audio.src = src;
    this.audio.load();
    this.audio.play();
  }

  pause() {
    if(this.audio.paused) this.audio.play();
    else this.audio.pause();
  }

}
