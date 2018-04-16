package clc3.webapi.mc;

import clc3.common.AbstractServlet;
import clc3.montecarlo.ServiceLocator;

public abstract class BaseServlet extends AbstractServlet {
    protected ServiceLocator serviceLocator = ServiceLocator.getInstance();
}
