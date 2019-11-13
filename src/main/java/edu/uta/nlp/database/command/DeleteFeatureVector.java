package edu.uta.nlp.database.command;

import edu.uta.nlp.constant.DataBaseType;
import edu.uta.nlp.database.factory.AbstractDataBase;
import edu.uta.nlp.database.factory.DataBaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hxy
 */
public class DeleteFeatureVector implements MysqlCmd {

    private String tableName;

    private int id;

    private static Logger logger = LoggerFactory.getLogger(DeleteFeatureVector.class);

    public DeleteFeatureVector(String tableName, int id) {
        this.tableName = tableName;
        this.id = id;
    }

    @Override
    public Object execute() throws Exception {

        StringBuilder sb = new StringBuilder("delete from " + tableName + " where 1=1");
        sb.append(" and id=" + id);

        logger.info("delete feature vector: " + sb.toString());

        DataBaseFactory dataBaseFactory = new DataBaseFactory();
        AbstractDataBase database = dataBaseFactory.getDatabase(DataBaseType.DATABASE_TYPE_MYSQL);
        Object result = database.operation(sb.toString());

        logger.info("delete feature vector result :" + result.toString());

        return result;
    }
}
