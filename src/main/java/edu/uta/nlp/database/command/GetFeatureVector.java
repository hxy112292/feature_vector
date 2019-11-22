package edu.uta.nlp.database.command;

import edu.stanford.nlp.util.StringUtils;
import edu.uta.nlp.constant.Constants;
import edu.uta.nlp.database.factory.AbstractDataBase;
import edu.uta.nlp.database.factory.DataBaseFactory;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.util.CamelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @author hxy
 */
public class GetFeatureVector implements MysqlCmd {

    private String tableName;

    FeatureVector featureVector;

    private static Logger logger = LoggerFactory.getLogger(GetFeatureVector.class);

    public GetFeatureVector(String tableName, FeatureVector featureVector) {
        this.tableName = tableName;
        this.featureVector = featureVector;
    }

    @Override
    public Object execute() throws Exception{

        StringBuilder sb = new StringBuilder("select * from " + tableName + " where 1 = 1");

        for (Field field : featureVector.getClass().getDeclaredFields()) {
            field.setAccessible(Boolean.TRUE);
            if(field.get(featureVector) != null
                    && !StringUtils.isNullOrEmpty(field.get(featureVector).toString())) {
                sb.append(" and " + CamelUtil.camel2under(field.getName()) + " ='");
                sb.append(field.get(featureVector).toString() + "'");
            }
        }

        logger.info("get feature vector: " + sb.toString());

        DataBaseFactory dataBaseFactory = new DataBaseFactory();
        AbstractDataBase database = dataBaseFactory.getDatabase(Constants.DATABASE_TYPE_MYSQL);
        Object result = database.operation(sb.toString(), FeatureVector.class);

        logger.info("get feature vector result: " + result.toString());

        return result;
    }
}
