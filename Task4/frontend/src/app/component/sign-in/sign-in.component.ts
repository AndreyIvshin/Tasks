import { Component, OnInit } from '@angular/core';
import {SecurityService} from '../../service/security.service';
import {UserAuth} from '../../model/user-auth';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  public user = new UserAuth();
  public notFound = false;
  public forbidden = false;

  constructor(private securityService: SecurityService, private router: Router) { }

  ngOnInit(): void {
  }

  signIn(): void {
    this.securityService.signIn(this.user).subscribe(value => {
      this.securityService.login(value);
      this.router.navigate(['/']);
    }, error => {
      console.log(error);
      const errorResponse = new HttpErrorResponse(error);
      this.notFound = errorResponse.status === 404;
      this.forbidden = errorResponse.status === 403;
    });
  }

}
