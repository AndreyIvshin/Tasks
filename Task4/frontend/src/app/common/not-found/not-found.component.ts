import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-not-found',
  template: `
    <span class="mat-display-1">Page Not Found</span>
  `,
  styles: [
  ]
})
export class NotFoundComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
