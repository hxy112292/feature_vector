package edu.uta.nlp.database.command;

import edu.uta.nlp.constant.DataBaseType;
import edu.uta.nlp.database.factory.AbstractDataBase;
import edu.uta.nlp.database.factory.DataBaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteFeatureVector implements MysqlCmd {

    private int id;

    private static Logger logger = LoggerFactory.getLogger(DeleteFeatureVector.class);

    public DeleteFeatureVector(int id) {
        this.id = id;
    }

    @Override
    public Object execute() throws Exception {

        StringBuilder sb = new StringBuilder("delete from feature_vector where 1=1");
        sb.append(" and id=" + id);

        logger.info("delete feature vector: " + sb.toString());

        DataBaseFactory dataBaseFactory = new DataBaseFactory();
        AbstractDataBase database = dataBaseFactory.getDatabase(DataBaseType.DATABASE_TYPE_MYSQL);
        database.open();
        Object result = database.executeSqlCmd(sb.toString());

        logger.info("delete feature vector result :" + result.toString());

        database.close();

        return result;
    }
}
