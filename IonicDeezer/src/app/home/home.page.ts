import { Component, OnInit } from '@angular/core';
import { Artist, DataSearchArtist, DeezerService, DataSearchAlbum, Album } from '../service/deezer.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {
  TAG: string = "HomePage";
  listArtist: Artist[];
  listAlbum: Album[];

  constructor(public deezerService: DeezerService, public router : Router) {}

  ngOnInit(){
  }

  //Méthode pour rechercher l'artiste ou l'auteur taper dans la barre de recherche
  onSearchArtist(event: any){
    let val = event.target.value;

    this.deezerService.getAuthors(val).then((result:DataSearchArtist) => {
      this.listArtist = result.data;
      })
  }

  //Méthode qui transmet le nom de l'artiste et qui nous amène vers la page des albums de l'artiste
  onClickArtist(artist: Artist){
    console.log(`${this.TAG}this.onClickArtist${artist.name}`);
    this.router.navigate(['list-album/' + artist.name]);
  }



}
