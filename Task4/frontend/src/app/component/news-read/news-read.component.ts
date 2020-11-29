import {Component, OnInit} from '@angular/core';
import {NewsService} from '../../service/news.service';
import {NewsFull} from '../../model/news-full';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {SecurityService} from '../../service/security.service';

@Component({
  selector: 'app-news-read',
  templateUrl: './news-read.component.html',
  styleUrls: ['./news-read.component.css']
})
export class NewsReadComponent implements OnInit {

  private id = 0;
  public news = new NewsFull();

  constructor(private newsService: NewsService, public securityService: SecurityService,
              private activatedRoute: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((map: ParamMap) => this.id = parseInt(map.get('id') || '0', 10));
    this.newsService.getNews(this.id).subscribe(value => this.news = value, error => console.log(error));
  }

  delete(): void {
    this.newsService.deleteNews(this.id).subscribe(value => this.router.navigate(['/news-list']));
  }

}
