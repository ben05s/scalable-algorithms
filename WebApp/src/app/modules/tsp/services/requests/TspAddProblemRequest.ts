import { TspCity } from '../../models/TspCity';

export class TspAddProblemRequest {
    name: string;
    cities: TspCity[];
}
