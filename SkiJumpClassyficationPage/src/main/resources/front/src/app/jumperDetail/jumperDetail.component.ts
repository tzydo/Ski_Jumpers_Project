import {Component, OnInit} from "@angular/core";
import {JumperSerivce} from "../service/jumper.service";
import {ActivatedRoute} from "@angular/router"
import {Jumper} from "../model/jumper";


@Component({
  selector: 'app-jumperDetail',
  templateUrl: './jumperDetail.component.html',
  styleUrls: ['./jumperDetail.component.css'],
  providers: [JumperSerivce],
})

// export class JumperDetail{
export class JumperDetail implements OnInit {

  rank: number;
  jumper: Jumper;

  constructor(private jumperService: JumperSerivce, private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      this.rank = params['rank'];
    });
  }

  ngOnInit() {
    this.jumperService.getOne(this.rank).subscribe(
      jumper => this.jumper = jumper
    );
  }
}

