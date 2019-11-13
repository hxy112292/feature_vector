package edu.uta.nlp.database.factory;

import edu.uta.nlp.util.ResultSetToList;

import java.util.List;

/**
 * @author hxy
 */
public abstract class AbstractDataBase {

    protected String driver;

    protected String url;

    protected String user;

    protected String password;

    public abstract void open() throws Exception;

    public abstract <T> Object executeSqlCmd(String sql, Class<T>... clazz) throws Exception;

    public abstract void close() throws Exception;

    public final <T> Object operation(String sql, Class<T>... clazz) throws Exception{

        open();

        Object result = executeSqlCmd(sql, clazz);

        close();

        return result;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
