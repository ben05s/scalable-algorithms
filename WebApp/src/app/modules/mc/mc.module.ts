import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { MaterialModule } from '../material.module';
import { RouterModule, Routes } from '@angular/router';

import { AngularFilePickerModule } from 'angular-file-picker';

import { McComponent } from './mc.component';
import { McTasksComponent } from './components/tasks/mc.tasks.component';
import { McSettingsComponent } from './components/settings/mc.settings.component';
import { McDetailComponent } from './components/detail/mc.detail.component';

import { McTasksService } from './services/tasks/mc.tasks.service';
import { MatDialogModule } from '@angular/material';

const mcRoutes: Routes = [{
    path: 'mc',
    component: McComponent,
    children: [
        // TODO (your sub routes)
    ]
}];

@NgModule({
    declarations: [
        McComponent,
        McTasksComponent,
        McSettingsComponent,
        McDetailComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        FormsModule,
        MaterialModule,
        AngularFilePickerModule,
        RouterModule.forRoot(mcRoutes),
    ],
    exports: [
        McComponent
    ],
    entryComponents: [
        McSettingsComponent,
        McDetailComponent
    ],
    providers: [
        McTasksService
    ],
    bootstrap: [ ]
})
export class McModule {}
