import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { MaterialModule } from '../material.module';
import { FormsUtilsModule } from '../forms.utils.module';

// TspComponents
import { TspComponent } from './tsp.component';
import { TspTourDiagramComponent } from './components/diagrams/tour/tsp.tour.diagram.component';

// problems components
import { TspProblemsComponent } from './components/problems/tsp.problems.component';
import { TspProblemsCreateComponent } from './components/problems/create/tsp.problems.create.component';
import { TspProblemsDetailsComponent } from './components/problems/details/tsp.problems.details.component';
// tasks components
import { TspTasksComponent } from './components/tasks/tsp.tasks.component';
import { TspTasksCreateComponent } from './components/tasks/create/tsp.tasks.create.component';
import { TspTasksDetailsComponent } from './components/tasks/details/tsp.tasks.details.component';
import { TspTasksDetailsConfigComponent } from './components/tasks/details/config/tsp.tasks.details.config.component';
import { TspTasksDetailsRunComponent } from './components/tasks/details/run/tsp.tasks.details.run.component';

// services
import { TspOperatorsService } from './services/operators/tsp.operators.service';
import { TspProblemService } from './services/problem/tsp.problem.service';
import { TspTaskService } from './services/task/tsp.task.service';
import { SortByPipe } from '../../utils/ArraySortPipe';


const tspRoutes: Routes = [{
    path: 'tsp', component: TspComponent,
    children: [
        { path: '', redirectTo: 'tasks', pathMatch: 'full' },
        { path: 'tasks', component: TspTasksComponent },
        { path: 'tasks/:taskId', component: TspTasksDetailsComponent },
        { path: 'problems', component: TspProblemsComponent },
        { path: 'problems/:problemId', component: TspProblemsDetailsComponent }
    ]
}];

@NgModule({
    declarations: [
        SortByPipe,
        TspComponent,
        TspTourDiagramComponent,
        TspProblemsComponent,
        TspProblemsCreateComponent,
        TspProblemsDetailsComponent,
        TspTasksComponent,
        TspTasksCreateComponent,
        TspTasksDetailsComponent,
        TspTasksDetailsConfigComponent,
        TspTasksDetailsRunComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MaterialModule,
        FormsUtilsModule,
        RouterModule.forRoot(tspRoutes),
    ],
    exports: [
        TspComponent
    ],
    entryComponents: [
        TspProblemsCreateComponent,
        TspTasksCreateComponent,
        TspTasksDetailsConfigComponent,
        TspTasksDetailsRunComponent
    ],
    providers: [
        TspOperatorsService,
        TspProblemService,
        TspTaskService
    ],
    bootstrap: [ ]
})
export class TspModule { }
