import {Component, OnInit} from '@angular/core';
import {NewsToSave} from '../../model/news-to-save';
import {ActivatedRoute, Router} from '@angular/router';
import {NewsService} from '../../service/news.service';

@Component({
  selector: 'app-news-add',
  templateUrl: './news-add.component.html',
  styleUrls: ['./news-add.component.css']
})
export class NewsAddComponent implements OnInit {

  public news = new NewsToSave();

  constructor(private newsService: NewsService, private activatedRoute: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    this.news.title = 'title';
    this.news.brief = 'brief';
    this.news.content = 'content';
  }

  add(): void {
    this.newsService.postNews(this.news).subscribe(value => {
        const strings = (value.headers.get('location') || '').split('/');
        this.router.navigate([`/news-read/${strings[strings.length - 1]}`]);
      },
      error => console.log(error));
  }

}
