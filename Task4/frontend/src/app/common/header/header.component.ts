import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-header',
  template: `
    <mat-toolbar color="primary">

      <button mat-button routerLink="/">
        <span class="mat-title">News Portal</span>
      </button>

      <button mat-button routerLink="/news-list">
        <span class="mat-title">News</span>
      </button>

      <span class="spacer"></span>

      <button mat-button [matMenuTriggerFor]="language">
        <span class="mat-subheading-2">Language</span>
        <mat-icon>arrow_drop_down</mat-icon>
      </button>
      <mat-menu #language>
        <button mat-menu-item>English</button>
        <button mat-menu-item>Russian</button>
      </mat-menu>

      <button mat-button routerLink="/sign-in">
        <span class="mat-subheading-2">Sign In</span>
      </button>

      <button mat-button routerLink="/sign-up">
        <span class="mat-subheading-2">Sign Up</span>
      </button>

    </mat-toolbar>
  `,
  styles: [`
    .spacer {
      flex: 1 1 auto;
    }`
  ]
})
export class HeaderComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

}
