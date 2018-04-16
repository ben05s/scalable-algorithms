package clc3.montecarlo.database.entities;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class MCTaskIteration {
    @Id
    Long id;
    @Index
    Key<MCTask> mcTask;
    @Index
    int iteration;
    MCSeasonResult seasonResult;

    public static Key<MCTaskIteration> key(long id) {
        return Key.create(MCTaskIteration.class, id);
    }

    public MCTaskIteration() {
    }

    public long getId() {
        return this.id;
    }

    public Key<MCTaskIteration> getKey() {
        return MCTaskIteration.key(this.id);
    }

    public Key<MCTask> getMcTask() {
        return mcTask;
    }

    public void setMcTask(Key<MCTask> mcTask) {
        this.mcTask = mcTask;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public MCSeasonResult getSeasonResult() {
        return seasonResult;
    }

    public void setSeasonResult(MCSeasonResult seasonResult) {
        this.seasonResult = seasonResult;
    }
}