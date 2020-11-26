import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {WelcomeComponent} from './pages/welcome/welcome.component';
import {SignInComponent} from './pages/sign-in/sign-in.component';
import {SignUpComponent} from './pages/sign-up/sign-up.component';
import {NewsListComponent} from './pages/news-list/news-list.component';
import {NotFoundComponent} from './common/not-found/not-found.component';
import {NewsReadComponent} from './pages/news-read/news-read.component';
import {NewsEditComponent} from './pages/news-edit/news-edit.component';

const routes: Routes = [
  {path: '', component: WelcomeComponent},
  {path: 'sign-in', component: SignInComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'news-list', component: NewsListComponent},
  {path: 'news-read', component: NewsReadComponent},
  {path: 'news-edit', component: NewsEditComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
