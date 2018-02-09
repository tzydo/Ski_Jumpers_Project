import {Component} from '@angular/core'
import {JumperSerivce} from "../service/jumper.service";
import {OnInit } from "@angular/core"
import {CountryService} from "../service/country.service";
import {AutoCompleteModule} from 'primeng/primeng';
import {Country} from "../model/country";


@Component({
  selector: 'app-addJumper',
  templateUrl: './addJumper.component.html',
  styleUrls: ['./addJumper.component.css'],
  providers: [JumperSerivce, CountryService, AutoCompleteModule],
})

export class AddJumper implements OnInit{

  jumpersCount: number;
  jumperFisCode: number;
  countryList: Country[];
  country: Country;
  surname: string;
  name: string;

  constructor(private jumperService: JumperSerivce, private countryService: CountryService) {}

  ngOnInit(): void {
   this.jumperService.getJumpersCount().subscribe(jumper => this.jumpersCount = jumper);
   this.jumperService.getJumperFisCode().subscribe(jumper => this.jumperFisCode = jumper);
   // this.countryService.getCountryList().subscribe(countries => this.countryList = countries);
  }

  countryFilter(event){
    let querry = event.query;
      this.countryService.getCountryListbyParam(querry).subscribe(
                (countries: any[] ) => {
                  this.countryList  = countries;
                },error => console.error(error)
      );
  }

  setCountry(country: any):void {
    console.log("set country : " + country.name);
    this.country = country;
  }

  onSubmit(){
    console.log("set country dziala : " + this.name + " " + this.country.name);
  }
}
