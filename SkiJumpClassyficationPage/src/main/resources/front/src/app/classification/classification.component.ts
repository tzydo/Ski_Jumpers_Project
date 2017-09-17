import {Component, OnInit} from '@angular/core'
import {JumperSerivce} from "../model/jumper.service";
import {Jumper} from "../model/jumper";

@Component({
  selector: 'app-classification',
  templateUrl: './classification.component.html',
  styleUrls: ['./classification.component.css'],
  providers: [JumperSerivce]
})

export class ClassificationComponent implements OnInit{

  jumpersList: Jumper[] = [];

  constructor(private jumperService: JumperSerivce){}

  ngOnInit(){
    this.jumperService.getAll().subscribe(
      (jumper: any[]) => {
        this.jumpersList = jumper;
      }, error => console.error(error)
    );
  }

}
