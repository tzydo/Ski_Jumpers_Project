import {Component, OnInit} from '@angular/core'
import {JumperSerivce} from "../service/jumper.service";
import {Jumper} from "../model/jumper";
import {Router} from "@angular/router";

@Component({
  selector: 'app-classification',
  templateUrl: './classification.component.html',
  styleUrls: ['./classification.component.css'],
  providers: [JumperSerivce]
})

export class ClassificationComponent implements OnInit {

  jumpersList: Jumper[] = [];

  constructor(private jumperService: JumperSerivce, private router: Router) {
  }

  onSelect(rank) {
    this.router.navigate(['/jumper-detail', rank]);
  }

  ngOnInit() {
    this.jumperService.getAll().subscribe(
      (jumper: any[]) => {
        this.jumpersList = jumper;
      }, error => console.error(error)
    );
  }

  deleteJumperbyRank(rank: any): void {
    for (var i = 0; i < this.jumpersList.length; i++) {
      if (this.jumpersList[i]["rank"] == rank) {
        this.jumpersList.splice(i, 1);
        this.jumperService.deleteJumperByRank(rank);
      }
    }
  }
}
