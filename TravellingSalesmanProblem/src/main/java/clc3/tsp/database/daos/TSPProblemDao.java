package clc3.tsp.database.daos;

import clc3.common.AbstractDao;
import clc3.tsp.database.entities.TSPProblem;

public class TSPProblemDao extends AbstractDao<TSPProblem> {
    @Override
    protected Class<TSPProblem> getEntityType() {
        return TSPProblem.class;
    }
}
