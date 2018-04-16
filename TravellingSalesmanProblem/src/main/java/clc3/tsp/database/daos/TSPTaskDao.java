package clc3.tsp.database.daos;

import clc3.common.AbstractDao;
import clc3.tsp.database.entities.TSPTask;

import java.util.Collection;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class TSPTaskDao extends AbstractDao<TSPTask> {
    protected Class<TSPTask> getEntityType() { return TSPTask.class; }

    public Collection<TSPTask> getAllOrderByCreated() {
        return ofy().load().type(getEntityType()).order("created").list();
    }
}