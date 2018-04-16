package clc3.tsp.database.daos;

import clc3.common.AbstractDao;
import clc3.tsp.database.entities.TSPTask;
import clc3.tsp.database.entities.TSPTaskConfig;
import com.googlecode.objectify.Key;

import java.util.Collection;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class TSPTaskConfigDao extends AbstractDao<TSPTaskConfig> {
    @Override
    protected Class<TSPTaskConfig> getEntityType() {
        return TSPTaskConfig.class;
    }

    public Collection<TSPTaskConfig> getByTaskKey(Key<TSPTask> taskKey) {
        return ofy().load().type(getEntityType())
                .filter("task", taskKey)
                .list();
    }
}
