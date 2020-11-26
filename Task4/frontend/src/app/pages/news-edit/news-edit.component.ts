import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-news-edit',
  template: `
    <mat-card class="card-padding">
      <mat-card-header class="title-center">
        <mat-card-title>Edit News</mat-card-title>
      </mat-card-header>
      <mat-card-content>
        <mat-form-field class="field">
          <mat-label>News Title</mat-label>
          <textarea matInput value="Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ea, reiciendis!">
          </textarea>
        </mat-form-field>
        <br>
        <mat-form-field class="field">
          <mat-label>News Brief</mat-label>
          <textarea matInput value="Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus autem
            debitis esse et, eum itaque magnam minus porro quas quis quo sed suscipit,
            ut vel, vitae voluptate voluptates. Officia, porro!">
          </textarea>
        </mat-form-field>
        <br>
        <mat-form-field class="field">
          <mat-label>News Content</mat-label>
          <textarea matInput value="Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus autem
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
              ut vel, vitae voluptate voluptates. Officia, porro!">
          </textarea>
        </mat-form-field>
        <br>
        <span class="footer-center">15/02/2020</span>
      </mat-card-content>
      <mat-card-actions align="end">
        <button mat-raised-button color="primary">Sign Up</button>
      </mat-card-actions>
    </mat-card>
  `,
  styles: [`
    .card-padding {
      padding: 2rem;
    }

    .title-center {
      display: flex;
      justify-content: center;
      margin-bottom: 1rem;
    }

    .field {
      width: 50vw;
    }
  `
  ]
})
export class NewsEditComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

}
