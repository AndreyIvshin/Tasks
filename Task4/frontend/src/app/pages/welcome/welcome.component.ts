import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-welcome',
  template: `
    <p>
      <span class="mat-display-1">Welcome to News Portal!</span>
    </p>
  `,
  styles: [
  ]
})
export class WelcomeComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
