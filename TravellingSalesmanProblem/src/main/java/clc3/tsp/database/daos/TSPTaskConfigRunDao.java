package clc3.tsp.database.daos;

import clc3.common.AbstractDao;
import clc3.tsp.database.entities.TSPTask;
import clc3.tsp.database.entities.TSPTaskConfig;
import clc3.tsp.database.entities.TSPTaskConfigRun;
import com.googlecode.objectify.Key;

import java.util.Collection;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class TSPTaskConfigRunDao extends AbstractDao<TSPTaskConfigRun> {
    @Override
    protected Class<TSPTaskConfigRun> getEntityType() {
        return TSPTaskConfigRun.class;
    }

    public Collection<TSPTaskConfigRun> getByTask(Key<TSPTask> task) {
        return ofy().load().type(getEntityType())
                .filter("task", task)
                .list();
    }

    public Collection<TSPTaskConfigRun> getByConfig(Key<TSPTaskConfig> config) {
        return ofy().load().type(getEntityType())
                .filter("config", config)
                .list();
    }
}
