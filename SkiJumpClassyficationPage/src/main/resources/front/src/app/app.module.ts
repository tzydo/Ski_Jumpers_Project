import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router'

import {AppComponent} from './home/app.component';
import {SearchComponent} from './search/search.component';
import {ClassificationComponent} from "./classification/classification.component";
import {AddJumper} from "./add/addJumper.component";
import {JumperSerivce} from "./service/jumper.service";
import {HttpModule} from "@angular/http";
import {FormsModule} from "@angular/forms";
import {CountryService} from "./service/country.service";
import {AutoCompleteModule} from 'primeng/primeng';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {FooterComponent} from "./footer/footer.component";

@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    ClassificationComponent,
    AddJumper,
    DashboardComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    AutoCompleteModule,
    RouterModule.forRoot([
      {
        path: ' ', redirectTo: 'home'
      }, {
        path: 'home',
        component: DashboardComponent
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
        path: '**', redirectTo: 'home', pathMatch: 'full'
      }
    ])
  ],
  providers: [JumperSerivce, CountryService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
