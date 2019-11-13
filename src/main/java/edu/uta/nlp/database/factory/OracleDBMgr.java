package edu.uta.nlp.database.factory;

public class OracleDBMgr extends AbstractDataBase{

    @Override
    public void open() throws Exception {

    }

    @Override
    public <T> Object executeSqlCmd(String sql, Class<T>... clazz) throws Exception {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
