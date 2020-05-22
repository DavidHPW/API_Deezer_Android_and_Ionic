import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn:'root'
})
export class DeezerService {
  TAG: string = "TagServiceDeezer";

  constructor(private http : HttpClient) { 

  }

  getAuthors(artist:string):Promise<DataSearchArtist>{
    console.log(`${this.TAG}this.getAuthors${artist}`);
    const url:string='https://api.deezer.com/search/artist?q=' + encodeURI(artist);
    console.log(`${this.TAG}url:${url}`);
    return new Promise(resolve=>{
      this.http.get(url).subscribe(data=>{
        let json:DataSearchArtist = data as DataSearchArtist;
        resolve(json);
      },err =>{
        console.log(err);
      });
    });
  }

  getAlbum(artist:string):Promise<DataSearchAlbum>{
    console.log(`${this.TAG}this.getAlbum${artist}`);
    const url:string='https://api.deezer.com/search/album?q=' + encodeURI(artist);
    console.log(`${this.TAG}url:${url}`);
    return new Promise(resolve=>{
      this.http.get(url).subscribe(data=>{
        let json:DataSearchAlbum = data as DataSearchAlbum;
        resolve(json)
      },err =>{
        console.log(err);
      });
    });
  }

  getTrack(album:number):Promise<DataSearchTrack>{
    console.log(`${this.TAG}this.getTrack${album}`);
    const url:string='https://api.deezer.com/album/' + album + '/tracks';
    console.log(`${this.TAG}url:${url}`);
    return new Promise(resolve=>{
      this.http.get(url).subscribe(data=>{
        let json:DataSearchTrack = data as DataSearchTrack;
        resolve(json)
      },err =>{
        console.log(err);
      });
    });
  }


}

export interface Artist {
  id: number;
  name: string;
  link: string;
  picture: string;
  picture_small: string;
  picture_medium: string;
  picture_big: string;
  picture_xl: string;
  nb_album: number;
  nb_fan: number;
  radio: boolean;
  tracklist: string;
  type: string;
}

export class DataSearchArtist {
  data: Artist[];
  total: number;
  next:string;
}

export interface Album {
  id: number;
  title: string;
  link: string;
  cover: string;
  cover_small: string;
  cover_medium: string;
  cover_big: string;
  cover_xl: string;
  genre_id: number;
  nb_tracks: number;
  record_type: string;
  tracklist: string;
  explicit_lyrics: boolean;
  artist: Artist;
  type: string;
}

export interface DataSearchAlbum {
  data: Album[];
  total: number;
  next: string;
}

export interface Track {
  id: number;
  readable: boolean;
  title: string;
  title_short: string;
  title_version: string;
  link: string;
  duration: number;
  rank: number;
  explicit_lyrics: boolean;
  explicit_content_lyrics: number;
  explicit_content_cover: number;
  preview: string;
  artist: Artist;
  type: string;
}

export interface DataSearchTrack {
  data: Track[];
}


