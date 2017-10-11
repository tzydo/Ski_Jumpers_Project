import {Injectable} from '@angular/core'
import {Http, Response} from '@angular/http'
import 'rxjs/add/operator/map'
import {Observable} from "rxjs";
import {Jumper} from "../model/jumper";

@Injectable()
export class JumperSerivce{
  constructor(private http: Http) {}

  getAll(): Observable<any>{
    return this.http.get('/api/jumpers/all')
                .map((response: Response) => {
                    return response.json();
                  }
                );
  }

  getJumpersByPatterns(rank: number, bib: number, fis_code: number, name: string,
                       surname: string, nationality: string):Observable<Jumper[]>{

    return this.http.get('/api/jumpers/get-jumpers-by-patterns?rank=' + rank +
                          '?bib=' + bib + '?fis_code=' + fis_code+'?name='+ name+
                          '?surname='+ surname + '?nationality='+ nationality )
                          .map((response: Response) => {
                              return response.json();
                          });
  }

  getJumpersCount(): Observable<number>{
    return this.http.get('api/jumpers/getCountJumpers')
                .map((response: Response) => {
                  return response.json();
                });
  }

  getJumperFisCode(): Observable<number>{
    return this.http.get('/api/jumpers/getNewFisCode')
          .map((response: Response)=> {
      return response.json();
    });
  }
}
