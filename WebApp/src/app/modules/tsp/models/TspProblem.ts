import { TspCity } from './TspCity';

export class TspProblem {
    id: number;
    name: string;
    cities: TspCity[] = new Array<TspCity>();
}
