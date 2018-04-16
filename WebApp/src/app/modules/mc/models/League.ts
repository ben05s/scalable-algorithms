import { Team } from "./Team";

export class League {
    leagueName: string;
    teams: Team[];

    constructor() {
        this.leagueName = "";
    }
}