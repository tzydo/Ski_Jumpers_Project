import {Component}  from '@angular/core'

@Component ({
  selector: 'app-footer',
  templateUrl: 'footer.component.html',
  styleUrls: ['footer.component.css']
})

export class FooterComponent{
  footerText: string = 'page was created using: Spring Boot, Rest, Batch, Hibernate, Maven, Bootstrap, Angular 2, Prime NG'
};

