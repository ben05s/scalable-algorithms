export class IdDescriptionEntry {
    id: number;
    description: string;
}
export class TspOperators {
    crossover: IdDescriptionEntry[] = [];
    mutation: IdDescriptionEntry[] = [];
    selection: IdDescriptionEntry[] = [];
}
