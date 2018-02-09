import {Injectable} from '@angular/core'
import {Http, Response, Headers} from '@angular/http'
import 'rxjs/add/operator/map'
import {Observable} from "rxjs";
import {Jumper} from "../model/jumper";
import {HttpParams} from "@angular/common/http";

@Injectable()
export class JumperSerivce {

  constructor(private http: Http) {
  }

  getOne(rank: number): Observable<any> {
    return this.http.get('/api/jumpers/'+rank).map((response: Response) => {return response.json();});
  }

  getAll(): Observable<any> {
    return this.http.get('/api/jumpers/all')
      .map((response: Response) => {
          return response.json();
        }
      );
  }

  getJumpersByPatterns(rank: number, bib: number, fis_code: number, name: string,
                       surname: string, nationality: string): Observable<Jumper[]> {
    // console.log(rank +" "+ bib +" "+ fis_code +" "+ name +" "+ surname +" "+ nationality);
    let querry: string = "?";

    if (rank) querry = querry.concat("&rank=" + rank);
    if (bib) querry = querry.concat("&bib=" + bib);
    if (fis_code) querry = querry.concat("&fis_code=" + fis_code);
    if (name) querry = querry.concat("&name=" + name);
    if (surname) querry = querry.concat("&surname=" + surname);
    if (nationality) querry = querry.concat("&nationality=" + nationality);
    // console.log(querry.toString());
    return this.http.get('/api/jumpers/get-jumpers-by-patterns?' + querry)
      .map((response: Response) => {
        return response.json();
      });
  }

  getJumpersCount(): Observable<number> {
    return this.http.get('api/jumpers/getCountJumpers')
      .map((response: Response) => {
        return response.json();
      });
  }

  getJumperFisCode(): Observable<number> {
    return this.http.get('/api/jumpers/getNewFisCode')
      .map((response: Response) => {
        return response.json();
      });
  }

  deleteJumperByRank(rank: number) {
    // console.log("/api/jumpers/delete/"+rank);
    // this.http.delete('/api/jumpers/delete/?rank='+rank)}


    console.log(rank.toString());
    var body = "rank=" + rank.toString();
    var headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    this.http.post("/api/jumpers/delete/?rank=" + rank, body, {headers: headers}).subscribe();
    // this.http.delete("/api/jumpers/delete/?rank="+rank).subscribe();
  }
}
