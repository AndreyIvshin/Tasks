import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-news-read',
  template: `
    <mat-card class="card">
      <mat-card-title class="title-center">
        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ea, reiciendis!
      </mat-card-title>
      <mat-card-subtitle>
        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus autem
        debitis esse et, eum itaque magnam minus porro quas quis quo sed suscipit,
        ut vel, vitae voluptate voluptates. Officia, porro!
      </mat-card-subtitle>
      <mat-card-content>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus autem
          debitis esse et, eum itaque magnam minus porro quas quis quo sed suscipit,
          ut vel, vitae voluptate voluptates. Officia, porro!
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus autem
          debitis esse et, eum itaque magnam minus porro quas quis quo sed suscipit,
          ut vel, vitae voluptate voluptates. Officia, porro!
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus autem
          debitis esse et, eum itaque magnam minus porro quas quis quo sed suscipit,
          ut vel, vitae voluptate voluptates. Officia, porro!
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus autem
          debitis esse et, eum itaque magnam minus porro quas quis quo sed suscipit,
          ut vel, vitae voluptate voluptates. Officia, porro!
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus autem
          debitis esse et, eum itaque magnam minus porro quas quis quo sed suscipit,
          ut vel, vitae voluptate voluptates. Officia, porro!
        </p>
        <span class="footer-center">15/02/2020</span>
      </mat-card-content>
      <mat-card-actions align="end">
        <button mat-raised-button color="primary" routerLink="/news-edit">Edit</button>
        <button mat-raised-button color="warn">Remove</button>
      </mat-card-actions>
    </mat-card>
  `,
  styles: [`
    .card {
      padding: 2rem;
      margin: 1rem;
    }

    .title-center {
      display: flex;
      justify-content: center;
      margin-bottom: 1rem;
    }

    .footer-center {
      display: flex;
      justify-content: center;
    }
  `
  ]
})
export class NewsReadComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

}
