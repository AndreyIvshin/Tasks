import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable, OperatorFunction, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {UserAuth} from '../model/user-auth';
import {UserReg} from '../model/user-reg';
import {Role} from '../model/role';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  constructor(private http: HttpClient, private r: Router) {
    SecurityService.router = r;
  }

  private static router: Router;

  private url = 'http://192.168.1.107:8085/api';
  private timeout: any;

  login(resp: HttpResponse<any>): void {
    this.timeout = setTimeout(this.logout, 5 * 60 * 1000);
    localStorage.setItem('token', (resp.headers.get('authorization') || '').split(' ')[1]);
    localStorage.setItem('role', resp.headers.get('role') || '');
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    SecurityService.router.navigate(['/']);
  }

  getToken(): string {
    return localStorage.getItem('token') || '';
  }

  getRole(): string {
    return localStorage.getItem('role') || '';
  }

  isAuthorized(): boolean {
    return !(this.getRole() === Role.guest);
  }

  isUser(): boolean {
    return (this.getRole() === Role.user);
  }

  isAdmin(): boolean {
    return (this.getRole() === Role.admin);
  }

  signIn(user: UserAuth): Observable<HttpResponse<any>> {
    return this.http.post(`${this.url}/auth`, user, {observe: 'response'}).pipe(this.handleError());
  }

  signUp(user: UserReg): Observable<HttpResponse<any>> {
    return this.http.post(`${this.url}/reg`, user, {observe: 'response'}).pipe(this.handleError());
  }

  private handleError(): OperatorFunction<any, any> {
    return catchError(err => {
      console.log(err.message);
      return throwError(err);
    });
  }
}
