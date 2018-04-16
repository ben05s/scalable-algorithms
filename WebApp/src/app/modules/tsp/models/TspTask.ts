import { TspProblem } from './TspProblem';
import { TspTaskConfigRunIteration } from './TspTaskConfigRunIteration';

export class TspTask {
    id: number;
    name: string;
    problem: TspProblem = new TspProblem();
    bestIteration: TspTaskConfigRunIteration = new TspTaskConfigRunIteration();
    created: string;
    started: string;
}
