package edu.uta.nlp.database.factory;

/**
 * @author hxy
 */
public abstract class AbstractDataBase {

    protected String driver;

    protected String url;

    protected String user;

    protected String password;

    public abstract void open() throws Exception;

    public abstract Object excuteSqlCmd(String sql) throws Exception;

    public abstract void close() throws Exception;

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
