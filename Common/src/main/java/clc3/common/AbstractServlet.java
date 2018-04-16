package clc3.common;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractServlet extends HttpServlet {
    private boolean post_;
    private boolean get_;
    
    protected Gson gson_ = new Gson();

    public AbstractServlet() {
        this.post_ = true;
        this.get_ = true;
    }

    public AbstractServlet(boolean post, boolean get) {
        this.post_ = post;
        this.get_ = get;
    }

    protected abstract void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected void setAccessControlHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
    }

    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAccessControlHeader(response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (post_) {
            setAccessControlHeader(response);
            process(request, response);
            response.setHeader("Access-Control-Allow-Origin", "*");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (get_) {
            setAccessControlHeader(response);
            process(request, response);
            response.setHeader("Access-Control-Allow-Origin", "*");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    protected void jsonResponse(HttpServletResponse response, Object object) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson_.toJson(object));
    }



    protected Object createEmptyParams() {
        return new Object();
    }

    protected <T> T getPostParameters(HttpServletRequest request, Class<T> clazz) throws IOException {
        return gson_.fromJson(request.getReader(), clazz);
    }

    protected Object getParameters(HttpServletRequest request) {
        Object params = createEmptyParams();
        parseRequest(request, params);
        return params;
    }

    protected void parseRequest(HttpServletRequest req, Object obj) {
        Set<String> names = new HashSet<String>();
        @SuppressWarnings("unchecked")
        Enumeration<String> enm = req.getParameterNames();
        while (enm.hasMoreElements()) {
            names.add(enm.nextElement());
        }
        Class clazz = obj.getClass();
        while (clazz != Object.class && !names.isEmpty()) {
            for (Field f: clazz.getDeclaredFields()) {
                if (!Modifier.isTransient(f.getModifiers())) {
                    String name = f.getName();
                    if (names.contains(name)) {
                        try {
                            names.remove(name);
                            f.setAccessible(true);
                            Object val = convertValue(req, f.getType(),
                                    name);
                            f.set(obj, val);
                        } catch (ParseException ex) {
                            //LOG.error("Error assigning field", ex);
                        } catch (IllegalAccessException ex) {
                            //LOG.error("Error assigning field", ex);
                        }
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private Object convertValue(HttpServletRequest req, Class<?> type,
                                String name) throws ParseException {
        if (type.isArray()) {
            Class<?> elemType = type.getComponentType();
            String strings[] = req.getParameterValues(name);
            if (strings == null || strings.length == 0) {
                return new Object[0];
            }
            Object array = Array.newInstance(elemType, strings.length);
            for (int i = 0; i < strings.length; ++i) {
                Object val = parse(elemType, strings[i]);
                Array.set(array, i, val);
            }
            return array;
        } else {
            String s = req.getParameter(name);
            if (s == null) {
                return null;
            }
            return parse(type, s);
        }
    }

    public static Object parse(Class<?> type, String value) throws ParseException {
        if (type == String.class) {
            return value;
        } else if (value == null || value.length() == 0) {
            return null;
        } else if (Enum.class.isAssignableFrom(type)) {
            @SuppressWarnings("unchecked")
            Object result = Enum.valueOf((Class<? extends Enum>)type, value);
            return result;
        } else if (type == boolean.class || type == Boolean.class) {
            return "true".equals(value);
        } else if (type == byte.class || type == Byte.class) {
            return Byte.valueOf(value);
        } else if (type == short.class || type == Short.class) {
            return Short.valueOf(value);
        } else if (type == int.class || type == Integer.class) {
            return Integer.valueOf(value);
        } else if (type == long.class || type == Long.class) {
            return Long.valueOf(value);
        } else if (type == float.class || type == Float.class) {
            return Float.valueOf(value);
        } else if (type == double.class || type == Double.class) {
            return Double.valueOf(value);
        } else if (type == Date.class) {
            return new SimpleDateFormat("dd/MM/yyyy").parse(value);
        } else if (type == BigDecimal.class) {
            DecimalFormat format = getDecimalFormat("0.00");
            return format.parse(value);
        } else {
            throw new RuntimeException("Cannot convert value of type " + type);
        }
    }

    private static DecimalFormat getDecimalFormat(String pattern) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat(pattern);
        format.setParseBigDecimal(true);
        format.setDecimalFormatSymbols(symbols);
        return format;
    }

}
