package edu.uta.nlp.database.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author hxy
 */
public class MysqlDBMgr extends AbstractDataBase {

    public Connection connection;

    public Statement statement;

    public ResultSet resultSet;

    @Override
    public void open() throws Exception{
        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);
    }

    @Override
    public Object excuteSqlCmd(String sql) throws Exception{
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    @Override
    public void close() throws Exception{
        resultSet.close();
        statement.close();
        connection.close();
    }
}
