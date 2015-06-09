package ru.qatools.properties.internal;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 04.06.15
 */
@SuppressWarnings("unused")
public class PropertyInfo {

    private String key;

    private String stringValue;

    private Object value;

    public PropertyInfo() {
    }

    public PropertyInfo(Object value) {
        this.value = value;
    }

    public PropertyInfo(String key, String stringValue, Object value) {
        this.key = key;
        this.stringValue = stringValue;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
