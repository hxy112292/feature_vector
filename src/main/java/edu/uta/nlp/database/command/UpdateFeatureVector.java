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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hxy
 */
public class UpdateFeatureVector implements MysqlCmd{

    private String tableName;

    private FeatureVector featureVector;

    private static Logger logger = LoggerFactory.getLogger(UpdateFeatureVector.class);

    public UpdateFeatureVector(String tableName, FeatureVector featureVector) {
        this.tableName = tableName;
        this.featureVector = featureVector;
    }

    @Override
    public Object execute() throws Exception {

        StringBuilder sb = new StringBuilder("update " + tableName + " set ");
        for (Field field : featureVector.getClass().getDeclaredFields()) {
            field.setAccessible(Boolean.TRUE);
            if(!field.getName().equals("id")
                    && !field.getName().equals("createTime")
                    && !field.getName().equals("updateTime")
                    && field.get(featureVector) != null
                    && !StringUtils.isNullOrEmpty(field.get(featureVector).toString())) {
                sb.append(CamelUtil.camel2under(field.getName()) + " ='");
                sb.append(field.get(featureVector).toString() + "',");
            }
        }

        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = simpleDate.format(new Date());
        sb.append("update_time = '" + timestamp + "'");
        sb.append("where id='" + featureVector.getId() + "'");

        logger.info("update feature vector: " + sb.toString());

        DataBaseFactory dataBaseFactory = new DataBaseFactory();
        AbstractDataBase database = dataBaseFactory.getDatabase(Constants.DATABASE_TYPE_MYSQL);
        Object result = database.operation(sb.toString());

        logger.info("update feature vector result: " + result.toString());

        return result;
    }
}
