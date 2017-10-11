import {Component} from '@angular/core'
import {JumperSerivce} from "../service/jumper.service";
import {Jumper} from "../model/jumper";
import {map} from "rxjs/operator/map";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})

export class SearchComponent{
  private message = 'Find by';
  private buttonText: 'search';
  private jumperList: any[];
  private rank: number;
  private bib: number;
  private fis_code: number;
  private name: string;
  private surname: string;
  private nationality: string;


  constructor(private jumperService: JumperSerivce){}

  public findJumperByPattern(rank, bib, fis_code, name, surname, nationality){
      this.rank = rank;
      this.bib = bib;
      this.fis_code = fis_code;
      this.name = name;
      this.surname;
      this.nationality = nationality;


      this.jumperService.getJumpersByPatterns(this.rank,this.bib,this.fis_code,
                                              this.name,this.surname,this.nationality)
                                    .subscribe((jumper: any[]) =>this.jumperList = jumper);
      console.log(this.jumperList);
  }
}
