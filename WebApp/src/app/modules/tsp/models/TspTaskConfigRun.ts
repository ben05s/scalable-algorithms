import { TspTaskConfigRunIteration } from './TspTaskConfigRunIteration';

export class TspTaskConfigRun {
    id: number;
    configId: number;
    latestIteration: TspTaskConfigRunIteration = new TspTaskConfigRunIteration();
    maxIteration: number;
    status: number;
    started: string;
    completed: string;
}
