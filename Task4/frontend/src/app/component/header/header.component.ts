import {Component, OnInit} from '@angular/core';
import {SecurityService} from '../../service/security.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private security: SecurityService, private router: Router) {
  }

  ngOnInit(): void {
  }

  isAuthorized(): boolean {
    return this.security.isAuthorized();
  }

  signOut(): void {
    this.security.logout();
    this.router.navigate(['/']);
  }

}
