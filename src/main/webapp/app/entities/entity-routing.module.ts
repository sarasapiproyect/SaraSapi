import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'language',
        data: { pageTitle: 'saraBusinessInteligenteApp.language.home.title' },
        loadChildren: () => import('./language/language.module').then(m => m.LanguageModule),
      },
      {
        path: 'intent',
        data: { pageTitle: 'saraBusinessInteligenteApp.intent.home.title' },
        loadChildren: () => import('./intent/intent.module').then(m => m.IntentModule),
      },
      {
        path: 'user-expresion',
        data: { pageTitle: 'saraBusinessInteligenteApp.userExpresion.home.title' },
        loadChildren: () => import('./user-expresion/user-expresion.module').then(m => m.UserExpresionModule),
      },
      {
        path: 'user-response',
        data: { pageTitle: 'saraBusinessInteligenteApp.userResponse.home.title' },
        loadChildren: () => import('./user-response/user-response.module').then(m => m.UserResponseModule),
      },
      {
        path: 'default-response',
        data: { pageTitle: 'saraBusinessInteligenteApp.defaultResponse.home.title' },
        loadChildren: () => import('./default-response/default-response.module').then(m => m.DefaultResponseModule),
      },
      {
        path: 'training',
        data: { pageTitle: 'saraBusinessInteligenteApp.training.home.title' },
        loadChildren: () => import('./training/training.module').then(m => m.TrainingModule),
      },
      {
        path: 'interations',
        data: { pageTitle: 'saraBusinessInteligenteApp.interations.home.title' },
        loadChildren: () => import('./interations/interations.module').then(m => m.InterationsModule),
      },
      {
        path: 'chatboot-style',
        data: { pageTitle: 'saraBusinessInteligenteApp.chatbootStyle.home.title' },
        loadChildren: () => import('./chatboot-style/chatboot-style.module').then(m => m.ChatbootStyleModule),
      },
      {
        path: 'channel',
        data: { pageTitle: 'saraBusinessInteligenteApp.channel.home.title' },
        loadChildren: () => import('./channel/channel.module').then(m => m.ChannelModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
