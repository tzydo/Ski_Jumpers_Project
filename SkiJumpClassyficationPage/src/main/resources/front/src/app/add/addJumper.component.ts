import {Component} from '@angular/core'
import {JumperSerivce} from "../model/jumper.service";
import {OnInit } from "@angular/core"

@Component({
  selector: 'app-addJumper',
  templateUrl: './addJumper.component.html',
  providers: [JumperSerivce]
})

export class AddJumper implements OnInit{

  jumpersCount: number;
  jumperFisCode: number;

  constructor(private jumperService: JumperSerivce) {}

  ngOnInit(): void {
   this.jumperService.getJumpersCount().subscribe(jumper => this.jumpersCount = jumper);
   this.jumperService.getJumperFisCode().subscribe(jumper => this.jumperFisCode = jumper);
  }
}
