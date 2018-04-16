import { Component, OnInit} from '@angular/core';

import { McTasksService } from './services/tasks/mc.tasks.service';
import { McTaskSettings } from './models/McTaskSettings';
import { McSettingsComponent } from './components/settings/mc.settings.component';

@Component({
  selector: 'mc',
  templateUrl: './mc.component.html',
  styleUrls: ['./mc.component.css']
})
export class McComponent implements OnInit {
  constructor() {}
  
  ngOnInit() {
  }
}