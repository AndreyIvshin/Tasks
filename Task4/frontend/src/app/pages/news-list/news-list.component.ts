import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-news-list',
  template: `
    <div class="cards">
      <mat-card class="card">
        <mat-card-title class="title-center">
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ea, reiciendis!
        </mat-card-title>
        <mat-card-content>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus autem
          debitis esse et, eum itaque magnam minus porro quas quis quo sed suscipit,
          ut vel, vitae voluptate voluptates. Officia, porro!
        </mat-card-content>
        <mat-card-footer class="footer-center">
          <span>15/02/2020</span>
        </mat-card-footer>
        <div align="end">
          <button mat-fab color="accent" class="read" routerLink="/news-read">
            <mat-icon>menu_book</mat-icon>
          </button>
        </div>
      </mat-card>
      <mat-card class="card">
        <mat-card-title class="title-center">
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ea, reiciendis!
        </mat-card-title>
        <mat-card-content>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus autem
          debitis esse et, eum itaque magnam minus porro quas quis quo sed suscipit,
          ut vel, vitae voluptate voluptates. Officia, porro!
        </mat-card-content>
        <mat-card-footer class="footer-center">
          15/02/2020
        </mat-card-footer>
      </mat-card>
    </div>
  `,
  styles: [`
    .cards {
      display: flex;
      flex-wrap: wrap;
      flex-direction: row;
      justify-content: center;
    }

    .card {
      padding: 2rem;
      margin: 1rem;
      width: 25vw;
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

    .read {
      position: absolute;
      right: -0.5rem;
      bottom: -0.5rem;
    }
  `]
})
export class NewsListComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

}
