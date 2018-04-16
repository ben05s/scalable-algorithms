export class McSettings { 
    roundsPerSeason: number;
    gamesPerMatch: number;
    roundsToSimulate: number;
    ratingSystem: number;
    useAdvWhite: boolean;
    useStrengthTrend: boolean;
    useStats: boolean;
    useRegularization: boolean;
    lineupStrategy: number;
    optimizeLineupTeamName: string;

    fileSeasonToSimulateContent: string;
    fileHistoricGamesContent: string;

    constructor() {
        this.roundsPerSeason = 11;
        this.gamesPerMatch = 6;
        this.roundsToSimulate = 11;
        this.ratingSystem = 1;
        this.useAdvWhite = false;
        this.useStrengthTrend = false;
        this.useStats = false;
        this.useRegularization = false;
        this.lineupStrategy = 4;
        this.optimizeLineupTeamName = "";
        this.fileSeasonToSimulateContent = "";
        this.fileHistoricGamesContent = "";
    }
    isComplete() {
        return this.ratingSystem != 0 && this.lineupStrategy != 0;
    }
}
