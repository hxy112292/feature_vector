package edu.uta.nlp.database.factory;

import edu.uta.nlp.constant.DataBaseType;

/**
 * @author hxy
 */
public class DataBaseFactory {

    public AbstractDataBase getDatabase(String databaseType) {
        if(databaseType.equals(DataBaseType.DATABASE_TYPE_MYSQL)) {
            return new MysqlDBMgr();
        } else {
            return null;
        }
    }
}
