import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sign-up',
  template: `
    <mat-card class="card-padding">
      <mat-card-header class="title-center">
        <mat-card-title>Sign Up</mat-card-title>
      </mat-card-header>
      <mat-card-content>
        <mat-form-field>
          <mat-label>Username</mat-label>
          <input matInput>
        </mat-form-field>
        <br>
        <mat-form-field>
          <mat-label>Password</mat-label>
          <input type="password" matInput>
        </mat-form-field>
        <br>
        <mat-form-field>
          <mat-label>Password Repeat</mat-label>
          <input type="password" matInput>
        </mat-form-field>
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
  `]
})
export class SignUpComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
