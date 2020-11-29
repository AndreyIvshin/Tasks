import {Component, OnInit} from '@angular/core';
import {UserReg} from '../../model/user-reg';
import {SecurityService} from '../../service/security.service';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  public user = new UserReg();
  public alreadyExist = false;
  public passwordsAreEqual = true;

  constructor(private securityService: SecurityService, private router: Router) {
  }

  ngOnInit(): void {
  }

  signUp(): void {
    this.securityService.signUp(this.user).subscribe(value => {
      this.securityService.login(value);
      this.router.navigate(['/']);
    }, error => {
      console.log(error);
      const errorResponse = new HttpErrorResponse(error);
      this.alreadyExist = errorResponse.status === 400;
    });
  }

  checkPasswords(): void {
    this.passwordsAreEqual = this.user.password === this.user.passwordRepeat;
  }
}
