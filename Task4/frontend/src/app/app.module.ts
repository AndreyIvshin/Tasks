import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HeaderComponent} from './common/header/header.component';
import {FooterComponent} from './common/footer/footer.component';
import {ContentComponent} from './common/content/content.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {WelcomeComponent} from './pages/welcome/welcome.component';
import {SignInComponent} from './pages/sign-in/sign-in.component';
import {SignUpComponent} from './pages/sign-up/sign-up.component';
import {NewsListComponent} from './pages/news-list/news-list.component';
import {NotFoundComponent} from './common/not-found/not-found.component';
import {MatCardModule} from '@angular/material/card';
import {MatInputModule} from '@angular/material/input';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatBadgeModule} from '@angular/material/badge';
import { NewsReadComponent } from './pages/news-read/news-read.component';
import { NewsEditComponent } from './pages/news-edit/news-edit.component';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ContentComponent,
    WelcomeComponent,
    SignInComponent,
    SignUpComponent,
    NewsListComponent,
    NotFoundComponent,
    NewsReadComponent,
    NewsEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatButtonToggleModule,
    MatCardModule,
    MatInputModule,
    MatGridListModule,
    MatBadgeModule,
    MatDialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
