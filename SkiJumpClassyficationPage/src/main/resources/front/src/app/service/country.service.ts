import {Injectable} from '@angular/core'
import {Http, Response} from '@angular/http'
import {Country} from "../model/country";
import {Observable} from "rxjs";


@Injectable()
export class CountryService {
  constructor (private http: Http) {};

  getCountryList(): Observable<Country[]> {
    return this.http.get("/api/jumpers/countries")
                  .map((response: Response) => {
                    return response.json();
                  })
  }

  getCountryListbyParam(param: string): Observable<any> {
    return this.http.get('api/jumpers/countries-by-pattern?pattern='.concat(param))
      .map((response: Response)=> {
        return response.json();
      })
  }
}
