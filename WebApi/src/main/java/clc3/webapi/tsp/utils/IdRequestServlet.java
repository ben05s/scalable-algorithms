package clc3.webapi.tsp.utils;

import clc3.common.servlets.requests.IdRequest;
import clc3.tsp.servlets.BaseServlet;

public abstract class IdRequestServlet extends BaseServlet {
    @Override
    protected Object createEmptyParams() {
        return new IdRequest();
    }
}
