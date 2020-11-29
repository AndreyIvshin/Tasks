import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {WelcomeComponent} from './component/welcome/welcome.component';
import {NotFoundComponent} from './component/not-found/not-found.component';
import {SignInComponent} from './component/sign-in/sign-in.component';
import {SignUpComponent} from './component/sign-up/sign-up.component';
import {NewsListComponent} from './component/news-list/news-list.component';
import {NewsReadComponent} from './component/news-read/news-read.component';
import {NewsWriteComponent} from './component/news-write/news-write.component';
import {NewsAddComponent} from './component/news-add/news-add.component';

const routes: Routes = [
  {path: '', component: WelcomeComponent},
  {path: 'sign-in', component: SignInComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'news-list', component: NewsListComponent},
  {path: 'news-add', component: NewsAddComponent},
  {path: 'news-read/:id', component: NewsReadComponent},
  {path: 'news-write/:id', component: NewsWriteComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
