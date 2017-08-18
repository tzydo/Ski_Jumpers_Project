import {Component, OnInit} from '@angular/core';
import {Http, Response} from "@angular/http";
import 'rxjs/Rx';
import {GetHttp} from "./httpGet.component";

@Component({
  selector: 'app-root',
  template: `
    Ho ho
    <h2 style="color: red">
        <div>{{title}} </div>
    </h2>
  `,
  styleUrls: ['./app.component.css'],
  providers: [GetHttp],
})

export class AppComponent implements OnInit{
  word: Title;
  title: string;
  constructor(private httpGet: GetHttp) {
  }

  ngOnInit() {
    this.httpGet.getHello().subscribe((data: any) => {
      this.word = data;
      this.title = data.title;
    }, (err) => {
      console.log(this.word)});
  }
}
export class Title {
  title: string;

  constructor(title: string){
    this.title = title;
  }
}

