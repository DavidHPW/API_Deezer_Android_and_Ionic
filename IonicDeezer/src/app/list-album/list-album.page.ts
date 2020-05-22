import { Component, OnInit } from '@angular/core';
import { Album, DeezerService, DataSearchAlbum } from '../service/deezer.service';
import { Router, ActivatedRoute } from '@angular/router';
import { stringify } from 'querystring';

@Component({
  selector: 'app-list-album',
  templateUrl: './list-album.page.html',
  styleUrls: ['./list-album.page.scss'],
})
export class ListAlbumPage implements OnInit {
  TAG: string = "ListAlbumPage";
  listAlbum : Album[];
  name : string;

  constructor(public deezerService: DeezerService, public router : Router, private activatedRoute : ActivatedRoute) {}

  ngOnInit() {
    //Récupération du nom de l'artiste pour lancer la recherche des albums
    this.name = this.activatedRoute.snapshot.paramMap.get('name')
    this.onSearchAlbum();
  }

  //Méthode qui lance la recherche d'album à partir du nom donné.
  onSearchAlbum(){
    this.deezerService.getAlbum(this.name).then((result:DataSearchAlbum) => {
      this.listAlbum = result.data;
      })
  }

  //Méthode qui transmet l'id et qui nous amène vers la page de la list-track 
  onClickAlbum(album: Album){
    console.log(`${this.TAG}this.onClickAlbum${album.id}`);
    this.router.navigate(['list-track/' + album.id]);
    console.log(`${this.TAG}this.router.navigate${['list-track/' + album.id]}`);
  }




}
