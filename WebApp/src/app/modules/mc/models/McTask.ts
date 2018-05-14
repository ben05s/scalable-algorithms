import { Team } from './Team';
import { McSettings } from './McSettings';

export class McTask {
    id: number;
    name: string;
    iterations: number;
    concurrentWorkers: number;
    mcSettings: McSettings;
    teams: Team[];
    status: number;
    progress: number = 0.0;
    created: Date;
    started: Date;
    completed: Date;
    duration: number;
}
