export class TspAddTaskConfigRequest {
    taskId: number;
    selection: number;
    crossover: number;
    mutation: number;
    mutationRate: number;
    elitism: boolean;
    populationSize: number;
    iterations: number;
    runs: number;
}
