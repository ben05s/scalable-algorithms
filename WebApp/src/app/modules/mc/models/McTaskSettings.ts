import { Team } from "./Team";
import { McSettings } from "./McSettings";

export class McTaskSettings {
    name: string;
    iterations: number;
    concurrentWorkers: number;
    mcSettings: McSettings;

    constructor() {
        this.name = "League Simulation";
        this.iterations = 1000;
        this.concurrentWorkers = 4;
        this.mcSettings = new McSettings();
    }

    isReady() {
        return  this.name && 
                this.iterations && 
                this.mcSettings.isComplete()
                ;
    }
}