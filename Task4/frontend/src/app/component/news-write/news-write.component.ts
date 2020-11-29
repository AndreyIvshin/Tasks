import {Component, OnInit} from '@angular/core';
import {NewsService} from '../../service/news.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NewsToSave} from '../../model/news-to-save';

@Component({
  selector: 'app-news-write',
  templateUrl: './news-write.component.html',
  styleUrls: ['./news-write.component.css']
})
export class NewsWriteComponent implements OnInit {

  private id = 0;
  public news = new NewsToSave();

  constructor(private newsService: NewsService, private activatedRoute: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((map: ParamMap) => this.id = parseInt(map.get('id') || '0', 10));
    this.newsService.getNews(this.id).subscribe(value => {
      this.news = new NewsToSave();
      this.news.title = value.title;
      this.news.brief = value.brief;
      this.news.content = value.content;
    }, error => console.log(error));
  }

  write(): void {
    this.newsService.putNews(this.id, this.news).subscribe(value => this.router.navigate([`/news-read/${this.id}`]),
        error => console.log(error));
  }

}
