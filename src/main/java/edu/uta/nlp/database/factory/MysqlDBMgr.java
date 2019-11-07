package edu.uta.nlp.database.factory;

import edu.uta.nlp.util.PropertiesUtil;
import edu.uta.nlp.util.StrUtil;

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
        driver = PropertiesUtil.getPropery("jdbc.driver");
        url = PropertiesUtil.getPropery("jdbc.url");
        user = PropertiesUtil.getPropery("jdbc.user");
        password = PropertiesUtil.getPropery("jdbc.password");
        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    @Override
    public Object executeSqlCmd(String sql) throws Exception {
        if(StrUtil.getFirstWord(sql).toLowerCase().equals("select")) {
            return executeSqlQueryCmd(sql);
        }
        else {
            return executeSqlUpdateCmd(sql);
        }
    }

    private ResultSet executeSqlQueryCmd(String sql) throws Exception {

        resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    private int executeSqlUpdateCmd(String sql) throws Exception {

        return statement.executeUpdate(sql);
    }

    @Override
    public void close() throws Exception {
        if(resultSet != null) {
            resultSet.close();
        }
        statement.close();
        connection.close();
    }
}
