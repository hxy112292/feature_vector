package edu.uta.nlp.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hxy
 */
public class ResultSetToList {

    public static <T> List<T> exchangeData(ResultSet resultSet, Class<T> clazz) throws Exception {
        Method[] declaredMethods = clazz.getMethods();
        List<T> list = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        while (resultSet.next()) {
            T obj = clazz.newInstance();
            for (Method method : declaredMethods) {
                String name = method.getName();
                if (!name.startsWith("set")){
                    continue;
                }
                String dbName = getDbName(name);
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    if (metaData.getColumnName(i).equals(dbName)) {
                        if (resultSet.getObject(i) != null) {
                            //
                            setValue(obj,method,resultSet,i);
                        }
                        break;
                    }
                }
            }
            list.add(obj);
        }
        return list;
    }

    private static <T> void setValue(T obj, Method method, ResultSet resultSet, int i) throws SQLException, InvocationTargetException, IllegalAccessException {
        String name = method.getParameterTypes()[0].getName().toLowerCase();
        if (name.contains("string")){
            method.invoke(obj, resultSet.getString(i));
        }
        else if (name.contains("short")){
            method.invoke(obj,resultSet.getShort(i));
        }
        else if (name.contains("int") || name.contains("integer")){
            method.invoke(obj,resultSet.getInt(i));
        }
        else if (name.contains("long")){
            method.invoke(obj,resultSet.getLong(i));
        }
        else if (name.contains("float")){
            method.invoke(obj,resultSet.getFloat(i));
        }
        else if (name.contains("double")){
            method.invoke(obj,resultSet.getDouble(i));
        }
        else if (name.contains("boolean")){
            method.invoke(obj,resultSet.getBoolean(i));
        }
        else if (name.contains("date")){
            method.invoke(obj,resultSet.getDate(i));
        }

        else {
            method.invoke(obj, resultSet.getObject(i));
        }

    }


    private static String getDbName(String name) {

        name = name.substring(3,4).toLowerCase()+name.substring(4);

        StringBuffer buffer = new StringBuffer();
        char[] nameChars = name.toCharArray();
        for (char nameChar : nameChars) {
            if (nameChar >= 'A' && nameChar <= 'Z') {
                buffer.append("_").append(String.valueOf(nameChar).toLowerCase());
            } else {
                buffer.append(String.valueOf(nameChar));
            }
        }
        return buffer.toString();
    }
}
