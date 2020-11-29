import {Component, OnInit} from '@angular/core';
import {NewsService} from '../../service/news.service';
import {NewsLite} from '../../model/news-lite';
import {SecurityService} from '../../service/security.service';

@Component({
  selector: 'app-news-list',
  templateUrl: './news-list.component.html',
  styleUrls: ['./news-list.component.css']
})
export class NewsListComponent implements OnInit {

  public allNews: NewsLite[] = [];

  constructor(private newsService: NewsService, public securityService: SecurityService) {
  }

  ngOnInit(): void {
    this.newsService.getAllNews().subscribe(value => this.allNews = value, error => console.log(error));
  }

}
