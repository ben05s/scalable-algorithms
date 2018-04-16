package clc3.tsp.servlets;

import clc3.common.AbstractServlet;
import clc3.tsp.ServiceLocator;

public abstract class BaseServlet extends AbstractServlet {
    protected ServiceLocator serviceLocator = ServiceLocator.getInstance();
}
