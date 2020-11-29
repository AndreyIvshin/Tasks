import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable, OperatorFunction, throwError} from 'rxjs';
import {NewsLite} from '../model/news-lite';
import {catchError} from 'rxjs/operators';
import {NewsFull} from '../model/news-full';
import {NewsToSave} from '../model/news-to-save';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  private url = 'http://192.168.99.100:8085/api/news';

  constructor(private http: HttpClient, private router: Router) {
  }

  getAllNews(): Observable<NewsLite[]> {
    return this.http.get<NewsLite[]>(this.url).pipe(this.handleError());
  }

  getNews(id: number): Observable<NewsFull> {
    return this.http.get<NewsFull>(`${this.url}/${id}`).pipe(this.handleError());
  }

  postNews(news: NewsToSave): Observable<HttpResponse<any>> {
    return this.http.post<any>(`${this.url}`, news, {observe: 'response'}).pipe(this.handleError());
  }

  putNews(id: number, news: NewsToSave): Observable<any> {
    return this.http.put<any>(`${this.url}/${id}`, news).pipe(this.handleError());
  }

  deleteNews(id: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/${id}`).pipe(this.handleError());
  }

  private handleError(): OperatorFunction<any, any> {
    return catchError(err => {
      console.log(err.message);
      return throwError(err);
    });
  }
}
