export class McSettings { 
    roundsPerSeason: number;
    gamesPerMatch: number;
    roundsToSimulate: number;
    useEloRating: boolean;
    useAdvWhite: boolean;
    useStrengthTrend: boolean;
    useStats: boolean;
    useRegularization: boolean;
    lineupStrategy: number;
    optimizeLineupTeamName: string;

    seasonFileName: string;

    fileSeasonToSimulateContent: string;
    fileHistoricGamesContent: string;

    constructor() {
        this.roundsPerSeason = 11;
        this.gamesPerMatch = 6;
        this.roundsToSimulate = 11;
        this.useEloRating = false;
        this.useAdvWhite = false;
        this.useStrengthTrend = false;
        this.useStats = false;
        this.useRegularization = false;
        this.lineupStrategy = 4;
        this.optimizeLineupTeamName = "";
        this.seasonFileName = "";
        this.fileSeasonToSimulateContent = "";
        this.fileHistoricGamesContent = "";
    }
    isComplete() {
        return this.lineupStrategy != 0;
    }
}
