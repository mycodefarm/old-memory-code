package com.jimo.test.tablesql;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimo on 17-8-27.
 */
public class TableCreator {
    public static void create(String className) throws ClassNotFoundException {
        Class<?> cl = Class.forName(className);
        DBTable dbTable = cl.getAnnotation(DBTable.class);
        if (dbTable == null) {
            System.err.println("No DBTable annotations in class : " + className);
            return;
        }
        String tableName = dbTable.name();
        //如果name为空，使用类名做表名
        if (tableName.length() < 1) {
            tableName = className.toLowerCase();
        }
        List<String> columns = new ArrayList<>();
        for (Field field : cl.getDeclaredFields()) {
            String columnName = null;
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations.length < 1) {
                continue;
            }
            if (annotations[0] instanceof SQLInteger) {
                SQLInteger sqlInteger = (SQLInteger) annotations[0];
                if (sqlInteger.name().length() < 1) {
                    columnName = field.getName().toLowerCase();
                } else {
                    columnName = sqlInteger.name();
                }
                columns.add(columnName + " INT" + getConstraints(sqlInteger.contraints()));
            }
            if (annotations[0] instanceof SQLString) {
                SQLString sqlString = (SQLString) annotations[0];
                if (sqlString.name().length() < 1) {
                    columnName = field.getName().toLowerCase();
                } else {
                    columnName = sqlString.name();
                }
                columns.add(columnName + " VARCHAR(" + sqlString.value() + ")" + getConstraints(sqlString.constraints()));
            }
        }
        StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + " (");
        for (String column : columns) {
            createCommand.append("\n       " + column + ",");
        }
        String tableSql = createCommand.substring(0, createCommand.length() - 1) + ");";
        System.out.println("类" + className + "的建表语句如下：\n" + tableSql);
    }

    private static String getConstraints(Constraints con) {
        String constraints = "";
        if (!con.allowNull()) {
            constraints += " NOT NULL";
        }
        if (con.primaryKey()) {
            constraints += " PRIMARY KEY";
        }
        if (con.unique()) {
            constraints += " UNIQUE";
        }
        return constraints;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        create("com.jimo.test.tablesql.Member");
        /*
        类com.jimo.test.tablesql.Member的建表语句如下：
        CREATE TABLE merber (
               firstname VARCHAR(30),
               lastname VARCHAR(50),
               age INT,
               handle VARCHAR(30) PRIMARY KEY);
        * */
    }
}
