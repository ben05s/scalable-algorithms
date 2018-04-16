import { Team } from './Team';

export class McTask {
    id: number;
    name: string;
    iterations: number;
    concurrentWorkers: number;
    useEloRating: boolean;
    teams: Team[];
    status: number;
    progress: number = 0.0;
    created: Date;
    started: Date;
    completed: Date;
    duration: number;
}
