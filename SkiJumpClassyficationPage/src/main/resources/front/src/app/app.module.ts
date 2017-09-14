import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router'

import {AppComponent} from './home/app.component';
import {SearchComponent} from './search/search.component';
import {ClassificationComponent} from "./classification/classification.component";
import {AddJumper} from "./add/addJumper.component";
import {JumperSerivce} from "./model/jumper.service";
import {HttpModule} from "@angular/http";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    ClassificationComponent,
    AddJumper
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    RouterModule.forRoot([
      {
        path: 'home',
        component: AppComponent
      }, {
        path: 'search',
        component: SearchComponent
      }, {
        path: 'classification',
        component: ClassificationComponent
      }, {
        path: 'add',
        component: AddJumper
      }, {
        path: ' ', redirectTo: '/home', pathMatch: 'full'
      }, {
        path: '**', redirectTo: 'home', pathMatch: 'full'
      }
    ])
  ],
  providers: [JumperSerivce],
  bootstrap: [AppComponent]
})
export class AppModule {
}
