import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';
import { HashLocationStrategy, Location, LocationStrategy } from '@angular/common';

import { McModule } from './modules/mc/mc.module';
import { TspModule } from './modules/tsp/tsp.module';
import { MaterialModule } from './modules/material.module';
import { FormsUtilsModule } from './modules/forms.utils.module';

import { AppComponent } from './app.component';
import { HomeComponent } from './shared/components/home/home.component';

const appRoutes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsUtilsModule,
    McModule,
    TspModule,
    RouterModule.forRoot(appRoutes)
  ],
  exports: [
    RouterModule
  ],
  providers: [Location, {provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }
