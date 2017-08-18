import {Injectable} from '@angular/core';
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs";

@Injectable()
export class GetHttp {

  constructor( private http: Http) {};

  getHello() {
    return this.http.get('/api/home')
      .map(
        (response: Response)=> {
          return response.json();
        })
  }
}
