package edu.uta.nlp.database.command;

import edu.stanford.nlp.util.StringUtils;
import edu.uta.nlp.constant.DataBaseType;
import edu.uta.nlp.database.factory.AbstractDataBase;
import edu.uta.nlp.database.factory.DataBaseFactory;
import edu.uta.nlp.entity.FeatureVector;
import edu.uta.nlp.util.CamelUtil;
import edu.uta.nlp.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hxy
 */
public class InsertFeatureVector implements MysqlCmd{

    private String tableName;

    private FeatureVector featureVector;

    private static Logger logger = LoggerFactory.getLogger(InsertFeatureVector.class);

    public InsertFeatureVector(String tableName, FeatureVector featureVector) {
        this.tableName = tableName;
        this.featureVector = featureVector;
    }

    @Override
    public Object execute() throws Exception {

        StringBuilder sb = new StringBuilder("insert into " + tableName + "(");

        for (Field field : featureVector.getClass().getDeclaredFields()) {
            field.setAccessible(Boolean.TRUE);
            if(!field.getName().equals("id")
                    && !field.getName().equals("createTime")
                    && !field.getName().equals("updateTime")
                    && field.get(featureVector) != null
                    && !StringUtils.isNullOrEmpty(field.get(featureVector).toString())) {
                sb.append(CamelUtil.camel2under(field.getName()) + ",");
            }
        }

        sb.append("create_time) values (");

        for (Field field : featureVector.getClass().getDeclaredFields()) {
            field.setAccessible(Boolean.TRUE);
            if(!field.getName().equals("id")
                    && !field.getName().equals("createTime")
                    && !field.getName().equals("updateTime")
                    && field.get(featureVector) != null
                    && !StringUtils.isNullOrEmpty(field.get(featureVector).toString())) {
                sb.append("'" + StrUtil.chRegex(field.get(featureVector).toString()) + "',");
            }
        }

        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = simpleDate.format(new Date());
        sb.append("'" + timestamp + "')");

        logger.info("insert feature vector: " + sb.toString());

        DataBaseFactory dataBaseFactory = new DataBaseFactory();
        AbstractDataBase database = dataBaseFactory.getDatabase(DataBaseType.DATABASE_TYPE_MYSQL);
        database.open();
        Object result = database.executeSqlCmd(sb.toString());

        logger.info("insert feature vector result: " + result.toString());

        database.close();

        return result;
    }
}
