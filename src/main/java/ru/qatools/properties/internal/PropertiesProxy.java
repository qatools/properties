package ru.qatools.properties.internal;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 03.06.15
 */
public class PropertiesProxy implements InvocationHandler {

    protected final Map<Method, PropertyInfo> properties;

    public PropertiesProxy(Map<Method, PropertyInfo> properties) {
        this.properties = properties;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PropertyInfo property = properties.get(method);
        if (property != null) {
            return property.getValue();
        }
        Class<?> type = method.getReturnType();
        return type.isPrimitive() ? Array.get(Array.newInstance(type, 1), 0) : null;
    }
}
