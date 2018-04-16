import { TspTaskProgressDetails } from './TspTaskProgressDetails';

export class TspTaskProgress {
    taskDetails: TspTaskProgressDetails = new TspTaskProgressDetails();
    taskConfigProgress: Map<number, TspTaskProgressDetails> = new Map<number, TspTaskProgressDetails>();
    taskConfigRunProgress: Map<number, TspTaskProgressDetails> = new Map<number, TspTaskProgressDetails>();
    bestTour: number[] = [];
}
