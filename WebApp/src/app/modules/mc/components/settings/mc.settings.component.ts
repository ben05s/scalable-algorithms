import { Component, Inject, Output } from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

import { PickedFile, ReadMode } from 'angular-file-picker';

import { Subscription } from 'rxjs/Subscription';
import { OnDestroy } from '@angular/core/src/metadata/lifecycle_hooks';
import { McTasksService } from '../../services/tasks/mc.tasks.service';
import { McTaskSettings } from '../../models/McTaskSettings';
import { Team } from '../../models/Team';
import { McTask } from '../../models/McTask';
import { League } from '../../models/League';

@Component({
  templateUrl: './mc.settings.component.html',
  styleUrls: ['./mc.settings.component.css']
})
export class McSettingsComponent {
  
  isLoading: boolean;
  readMode: ReadMode = ReadMode.text;
  file: PickedFile;
  fileH: PickedFile;
  settings: McTaskSettings = new McTaskSettings();
  league: League = new League();
  task: McTask;

  lineupStrategies = [
    {value: 'Avoid Strong Opponents', id: 1},
    {value: 'Match Strong Opponents', id: 2},
    {value: 'White/Black Performance', id: 3},
    {value: 'Descending Rating Strength', id: 4},
    {value: 'Ascending Rating Strength', id: 5},
    {value: 'Random', id: 6},
    {value: 'Traditional', id: 7}
  ];

  constructor(public dialogRef: MatDialogRef<McSettingsComponent>,
    private _tasksService: McTasksService) {}

  uploadLeaguePgn(file: PickedFile): void {
    if(!file) return;
    this.isLoading = true;
    this._tasksService.uploadPgnFile(file.name, file.content).subscribe(league => {
      this.isLoading = false;
      this.league = league;
      if(!this.settings.name) this.settings.name = league.leagueName;
    });
  }

  addTask(): void {
    this.settings.mcSettings.fileSeasonToSimulateContent = this.file.content;
    if(this.fileH) this.settings.mcSettings.fileHistoricGamesContent = this.fileH.content;
    this._tasksService.addTask(this.settings).subscribe(task => {
      this.dialogRef.close(task);
    });
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
