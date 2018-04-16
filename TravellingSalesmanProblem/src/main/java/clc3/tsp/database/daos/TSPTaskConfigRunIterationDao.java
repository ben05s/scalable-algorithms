package clc3.tsp.database.daos;

import clc3.common.AbstractDao;
import clc3.tsp.database.entities.TSPTaskConfigRun;
import clc3.tsp.database.entities.TSPTaskConfigRunIteration;
import com.googlecode.objectify.Key;

import java.util.Collection;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class TSPTaskConfigRunIterationDao extends AbstractDao<TSPTaskConfigRunIteration> {
    @Override
    protected Class<TSPTaskConfigRunIteration> getEntityType() {
        return TSPTaskConfigRunIteration.class;
    }

    public Collection<TSPTaskConfigRunIteration> getByTaskConfigRun(Key<TSPTaskConfigRun> taskConfigRunKey) {
        return ofy().load().type(getEntityType())
                .filter("taskConfigRun", taskConfigRunKey)
                .list();
    }
    // note: the following methods requires a index.yaml in the default project to work!
    // as soon as more than one property is used to filter/order
    // an index.yaml is needed to define the index @index alone is not enough
    public Collection<TSPTaskConfigRunIteration> getByTaskConfigRunOrderByIteration(Key<TSPTaskConfigRun> taskConfigRunkey) {
        return ofy().load().type(getEntityType())
                .filter("taskConfigRun", taskConfigRunkey)
                .order("-iteration")
                .list();
    }

    public TSPTaskConfigRunIteration getLatestIteration(Key<TSPTaskConfigRun> taskConfigRunkey) {
        return ofy().load().type(getEntityType())
                .filter("taskConfigRun", taskConfigRunkey)
                .order("-iteration")
                .limit(1).first().now();
    }

    public TSPTaskConfigRunIteration getByTaskConfigRunAndIteration(Key<TSPTaskConfigRun> key, int iteration) {
        return ofy().load().type(getEntityType())
                .filter("taskConfigRun", key)
                .filter("iteration", iteration)
                .first().now();
    }
}