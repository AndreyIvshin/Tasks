import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  template: `
    <mat-toolbar>
      <span class="mat-body">Copyright &copy; EPAM 2020. All rights reserved.</span>
    </mat-toolbar>
  `,
  styles: [
  ]
})
export class FooterComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
